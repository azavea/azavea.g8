package $package$.api.commands

import cats.implicits._
import com.monovore.decline.Opts
import cats.effect._
import doobie.util.transactor.Transactor
import doobie.implicits._
import com.lightbend.emoji.ShortCodes.Implicits._
import com.lightbend.emoji.ShortCodes.Defaults._
import com.monovore.decline._
import eu.timepit.refined.types.numeric._
import com.monovore.decline.refined._

import scala.util.Try

trait DatabaseOptions {

  private val databasePortHelp = "Port to connect to database on"
  private val databasePort = (Opts
    .option[PosInt]("db-port", help = databasePortHelp)
    .orElse Opts.env[PosInt]("POSTGRES_PORT", help=databasePortHelp))
    .withDefault(PosInt($default_db_port$))

  private val databaseHostHelp = "Database host to connect to"
  private val databaseHost = (Opts
    .option[String]("db-host", help = databaseHostHelp)
    .orElse Opts.env[String]("POSTGRES_HOST", help=databaseHostHelp))
    .withDefault("localhost")

  private val databaseNameHelp = "Database name to connect to"
  private val databaseName = (Opts
    .option[String]("db-name", help = databaseNameHelp)
    .orElse Opts.env[String]("POSTGRES_NAME", help = databaseNameHelp))
    .withDefault("$name;format="norm"$")

  private val databasePasswordHelp = "Database password to use"
  private val databasePassword = (Opts
    .option[String]("db-password", help = databasePasswordHelp)
    .orElse Opts.env[String]("POSTGRES_PASSWORD", help = databasePasswordHelp))
    .withDefault("$name;format="norm"$")

  private val databaseUserHelp = "User to connect with database with"
  private val databaseUser = (Opts
    .option[String]("db-user", help = databaseUserHelp)
    .orElse Opts.env[String]("POSTGRES_USER", help = databaseUserHelp))
    .withDefault("$name;format="norm"$")

  def databaseConfig(
      implicit contextShift: ContextShift[IO]): Opts[DatabaseConfig] =
    ((
      databaseUser,
      databasePassword,
      databaseHost,
      databasePort,
      databaseName
    ) mapN DatabaseConfig).validate(
      e":boom: Unable to connect to database - please ensure database is configured and listening at entered port"
    ) { config =>
      val xa =
        Transactor
          .fromDriverManager[IO](config.driver,
                                 config.jdbcUrl,
                                 config.dbUser,
                                 config.dbPass)
      val select = Try {
        fr"SELECT 1".query[Int].unique.transact(xa).unsafeRunSync()
      }
      select.toEither match {
        case Right(_) => true
        case Left(_)  => false
      }
    }
}
