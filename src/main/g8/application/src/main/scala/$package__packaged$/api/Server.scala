package $package$.api

import $package$.api.commands.{ApiConfig, Commands, DatabaseConfig}
import $package$.api.endpoints.UserEndpoints
import $package$.api.services.UsersService

import cats.effect._
import cats.implicits._
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import org.http4s.implicits._
import org.http4s.server.blaze._
import org.http4s.server.middleware._
import org.http4s.server.{Router, Server => HTTP4sServer}
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

object Server extends IOApp {

  private def createServer(
      apiConfig: ApiConfig,
      dbConfig: DatabaseConfig
  ): Resource[IO, HTTP4sServer[IO]] =
    for {
      connectionEc <- ExecutionContexts.fixedThreadPool[IO](2)
      blocker <- Blocker[IO]
      xa <- HikariTransactor.newHikariTransactor[IO](
        "org.postgresql.Driver",
        dbConfig.jdbcUrl,
        dbConfig.dbUser,
        dbConfig.dbPass,
        connectionEc,
        blocker
      )
      allEndpoints = UserEndpoints.endpoints
      docs = allEndpoints.toOpenAPI("$name$", "0.0.1")
      docRoutes = new SwaggerHttp4s(docs.toYaml, "open-api", "spec.yaml")
        .routes[IO]
      userRoutes = new UsersService[IO](xa).routes
      router = CORS(
        Router(
          "/api" -> ResponseLogger
            .httpRoutes(false, false)(userRoutes <+> docRoutes)
        )
      ).orNotFound
      server <- {
        BlazeServerBuilder[IO](blocker.blockingContext)
          .bindHttp(apiConfig.internalPort.value, "0.0.0.0")
          .withHttpApp(router)
          .resource
      }
    } yield {
      server
    }

  override def run(args: List[String]): IO[ExitCode] = {
    import Commands._

    applicationCommand.parse(args, env = sys.env) map {
      case RunServer(apiConfig, dbConfig) =>
        createServer(apiConfig, dbConfig)
          .use(_ => IO.never)
          .as(ExitCode.Success)
      case RunMigrations(config) => runMigrations(config)
    } match {
      case Left(e) =>
        IO {
          println(e.toString())
        } map { _ =>
          ExitCode.Error
        }
      case Right(s) => s
    }
  }
}
