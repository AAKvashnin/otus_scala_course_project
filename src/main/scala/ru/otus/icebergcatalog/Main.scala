package ru.otus.icebergcatalog

import zio._
import zio.logging._
object Main extends App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    App.server.exitCode
}