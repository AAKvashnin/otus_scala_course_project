scalaVersion := "2.13.12"

name := "scala-icebergcatalog"
organization := "ru.otus"
version := "1.0"

libraryDependencies ++= Dependencies.zio
libraryDependencies ++= Dependencies.zioConfig
libraryDependencies += Dependencies.zioHttp
libraryDependencies += Dependencies.liquibase
libraryDependencies += Dependencies.postgres
libraryDependencies ++= Dependencies.quill
libraryDependencies ++= Dependencies.circe

scalacOptions += "-Ymacro-annotations"