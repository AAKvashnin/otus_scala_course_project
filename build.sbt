import sbt._
import Keys._


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
libraryDependencies += Dependencies.logback

scalacOptions += "-Ymacro-annotations"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}