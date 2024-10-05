scalaVersion := "2.13.12"

libraryDependencies ++= Dependencies.zio
libraryDependencies ++= Dependencies.zioConfig
libraryDependencies += Dependencies.zioHttp
libraryDependencies += Dependencies.liquibase
libraryDependencies += Dependencies.postgres
libraryDependencies ++= Dependencies.quill