Global / cancelable := true
Global / onLoad ~= (_ andThen ("project application" :: _))

import sbt._
import sbtwelcome._

lazy val noWelcome = Seq(
  onLoadMessage := ""
)

logo :=
  """
    |                                                         aaaa
    |                                                        a    a
    |  aaaa  aaaaa  aaaa  a    a  aaaa    aaaa        aaaaa  a    a
    | a    a     a a    a a    a aa  aa  a    a      aa  aa  a    a
    |      a    a       a  a  a  a    a       a      a    a   aaaa
    |  aaaaa   a    aaaaa  a  a  aaaaaa   aaaaa      a    a  a    a
    | a    a  a    a    a  a  a  a       a    a      a    a  a    a
    | a   aa a     a   aa   aa   aa   a  a   aa  a   aa  aa  a    a
    |  aaa a aaaaa  aaa a   aa    aaaa    aaa a  a    aaaaa   aaaa
    |                                                     a
    |                                                 a  aa
    |                                                  aaa
  """.stripMargin

usefulTasks := Seq(
  UsefulTask("a", "~application/compile", "Compile the application with file watch enabled"),
  UsefulTask("b", "application/compile", "Compile the application without file watch enabled"),
  UsefulTask("c", "application/test", "Run tests in the application subproject"),
  UsefulTask("d", "application/testOnly *FooSpec", "Run tests in the application subproject with class names ending with *FooSpec"),
  UsefulTask("e", "fix", "Run scalafmt, scalafix, and scalafmtSbt"),
  UsefulTask("f", "projects", "View available subprojects")
)

addCommandAlias(
  "fix",
  ";scalafix;scalafmt;scalafmtSbt"
)

// Versions
val CirceFs2Version         = "0.13.0"
val CirceVersion            = "0.13.0"
val DeclineVersion          = "1.2.0"
val DoobieVersion           = "0.9.0"
val EmojiVersion            = "1.2.1"
val FlywayVersion           = "6.5.0"
val GeotrellisServerVersion = "4.2.0"
val Http4sVersion           = "0.21.5"
val Log4CatsVersion         = "1.1.1"
val PostGISVersion          = "2.5.0"
val PureConfigVersion       = "0.12.1"
val RefinedVersion          = "0.9.14"
val ScapegoatVersion        = "1.3.11"
val Slf4jVersion            = "1.7.30"
val Specs2Version           = "4.10.0"
val TapirVersion            = "0.16.1"

// Dependencies
val circeCore = "io.circe" %% "circe-core" % CirceVersion
val circeFs2 = "io.circe" %% "circe-fs2" % CirceFs2Version
val circeGeneric = "io.circe" %% "circe-generic" % CirceVersion
val circeRefined = "io.circe" %% "circe-refined" % CirceVersion
val decline = "com.monovore" %% "decline" % DeclineVersion
val declineRefined = "com.monovore" %% "decline-refined" % DeclineVersion
val doobie = "org.tpolecat" %% "doobie-core" % DoobieVersion
val doobieHikari = "org.tpolecat" %% "doobie-hikari" % DoobieVersion
val doobiePostgres = "org.tpolecat" %% "doobie-postgres" % DoobieVersion
val doobiePostgresCirce = "org.tpolecat" %% "doobie-postgres-circe" % DoobieVersion
val doobieRefined = "org.tpolecat" %% "doobie-refined" % DoobieVersion
val doobieScalatest = "org.tpolecat" %% "doobie-scalatest" % DoobieVersion % "test"
val doobieSpecs2 = "org.tpolecat" %% "doobie-specs2" % DoobieVersion % "test"
val emoji = "com.lightbend" %% "emoji" % EmojiVersion
val flyway = "org.flywaydb" % "flyway-core" % FlywayVersion
val geotrellisServerCore = "com.azavea.geotrellis" %% "geotrellis-server-core" % GeotrellisServerVersion
val http4s = "org.http4s" %% "http4s-blaze-server" % Http4sVersion
val http4sCirce = "org.http4s" %% "http4s-circe" % Http4sVersion
val http4sDsl = "org.http4s" %% "http4s-dsl" % Http4sVersion
val http4sServer = "org.http4s" %% "http4s-blaze-server" % Http4sVersion
val log4cats = "io.chrisdavenport" %% "log4cats-slf4j" % Log4CatsVersion
val postgis = "net.postgis" % "postgis-jdbc" % PostGISVersion
val pureConfig = "com.github.pureconfig" %% "pureconfig" % PureConfigVersion
val refined = "eu.timepit" %% "refined" % RefinedVersion
val refinedCats = "eu.timepit" %% "refined-cats" % RefinedVersion
val slf4jApi = "org.slf4j" % "slf4j-api" % Slf4jVersion
val slf4jSimple = "org.slf4j" % "slf4j-simple" % Slf4jVersion
val specs2Core = "org.specs2" %% "specs2-core" % Specs2Version % "test"
val tapir = "com.softwaremill.sttp.tapir" %% "tapir-core" % TapirVersion
val tapirCirce = "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % TapirVersion
val tapirHttp4sServer = "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % TapirVersion
val tapirOpenAPICirceYAML = "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % TapirVersion
val tapirOpenAPIDocs = "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % TapirVersion
val tapirSwaggerUIHttp4s = "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-http4s" % TapirVersion

// Enable a basic import sorter -- rules are defined in .scalafix.conf
ThisBuild / scalafixDependencies +=
  "com.github.liancheng" %% "organize-imports" % "0.4.3"

lazy val settings = Seq(
  organization := "$organization$",
  name := "$name;format="norm"$",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.12",
  scalafmtOnCompile := true,
  ThisBuild / scapegoatVersion := ScapegoatVersion,
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
  addCompilerPlugin(
    "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
  ),
  addCompilerPlugin(scalafixSemanticdb),
  autoCompilerPlugins := true,
  assembly / assemblyJarName := "application-assembly.jar",
  assembly / assemblyMergeStrategy := {
    case "reference.conf"                       => MergeStrategy.concat
    case "application.conf"                     => MergeStrategy.concat
    case n if n.startsWith("META-INF/services") => MergeStrategy.concat
    case n if n.endsWith(".SF") || n.endsWith(".RSA") || n.endsWith(".DSA") =>
      MergeStrategy.discard
    case "META-INF/MANIFEST.MF" => MergeStrategy.discard
    case _                      => MergeStrategy.first
  },
  excludeDependencies ++= Seq(
    "log4j"     % "log4j",
    "org.slf4j" % "slf4j-log4j12",
    "org.slf4j" % "slf4j-nop"
  ),
  externalResolvers := Seq(
    DefaultMavenRepository,
    Resolver.sonatypeRepo("snapshots"),
    Resolver.typesafeIvyRepo("releases"),
    Resolver.bintrayRepo("azavea", "maven"),
    Resolver.bintrayRepo("azavea", "geotrellis"),
    "locationtech-releases" at "https://repo.locationtech.org/content/groups/releases",
    "locationtech-snapshots" at "https://repo.locationtech.org/content/groups/snapshots",
    Resolver.bintrayRepo("guizmaii", "maven"),
    Resolver.bintrayRepo("colisweb", "maven"),
    "jitpack".at("https://jitpack.io"),
    Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(
      Resolver.ivyStylePatterns
    )
  ),
  run / fork := true,
  shellPrompt := { s =>
    Project.extract(s).currentProject.id + " > "
  }
)

lazy val dependencies = Seq(
  specs2Core,
  circeCore,
  circeGeneric,
  circeRefined,
  decline,
  declineRefined,
  doobie,
  doobieHikari,
  doobiePostgres,
  doobiePostgresCirce,
  doobieRefined,
  doobieScalatest,
  doobieSpecs2,
  emoji,
  flyway,
  geotrellisServerCore,
  http4s,
  http4sCirce,
  http4sDsl,
  http4sServer,
  log4cats,
  postgis,
  pureConfig,
  refined,
  refinedCats,
  slf4jApi,
  slf4jSimple,
  tapir,
  tapirCirce,
  tapirHttp4sServer,
  tapirOpenAPICirceYAML,
  tapirOpenAPIDocs,
  tapirSwaggerUIHttp4s
)

lazy val application = (project in file("application"))
  .settings(settings: _*)
  .settings({
    libraryDependencies ++= dependencies
  })
  .settings(noWelcome: _*)

lazy val applicationRef = LocalProject("application")
