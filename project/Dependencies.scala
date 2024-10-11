import sbt.Keys.libraryDependencies
import sbt._

object Dependencies {
  lazy val ZioVersion = "1.0.4"
  lazy val ZIOHttpVersion = "1.0.0.0-RC27"
  lazy val LiquibaseVersion = "3.4.2"
  lazy val PostgresVersion = "42.3.1"
  lazy val CirceVersion = "0.14.2"


  lazy val zio: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio" % ZioVersion,
    "dev.zio" %% "zio-test" % ZioVersion,
    "dev.zio" %% "zio-test-sbt" % ZioVersion,
    "dev.zio" %% "zio-macros" % ZioVersion
  )

  lazy val zioConfig: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-config" % "1.0.5",
    "dev.zio" %% "zio-config-magnolia" % "1.0.5",
    "dev.zio" %% "zio-config-typesafe" % "1.0.5"
  )

  lazy val zioHttp = "io.d11" %% "zhttp" % ZIOHttpVersion

  lazy val quill = Seq(
    "io.getquill" %% "quill-jdbc-zio" % "3.12.0",
    "io.github.kitlangton" %% "zio-magic" % "0.3.11"
  )

  lazy val liquibase = "org.liquibase" % "liquibase-core" % LiquibaseVersion

  lazy val postgres = "org.postgresql" % "postgresql" % PostgresVersion

  lazy val circe = Seq(
    "io.circe" %% "circe-core" % CirceVersion,
    "io.circe" %% "circe-generic" % CirceVersion,
    "io.circe" %% "circe-parser" % CirceVersion,
    "io.circe" %% "circe-derivation" % "0.13.0-M4",
    "org.http4s" %% "http4s-circe" % "0.23.14"
  )

}