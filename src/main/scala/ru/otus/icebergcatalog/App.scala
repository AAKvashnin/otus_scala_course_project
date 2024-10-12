package ru.otus.icebergcatalog

import ru.otus.icebergcatalog.api.IcebergCatalogAPI
import ru.otus.icebergcatalog.services.IcebergCatalogService
import zio.blocking.Blocking
import zio.clock.Clock
import zio.random.Random
object App {

  type AppEnvironment = IcebergCatalogService.IcebergCatalogService with
    Clock with Blocking
    with Random

  val appEnvironment =  Blocking.live >+> IcebergCatalogService.live

  val httpApp = IcebergCatalogAPI.api

  val server =
    zhttp.service.Server.start(8080, httpApp)
      .provideCustomLayer(appEnvironment)


}