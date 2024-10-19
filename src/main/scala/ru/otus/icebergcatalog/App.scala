package ru.otus.icebergcatalog

import ru.otus.icebergcatalog.api.IcebergCatalogAPI
import ru.otus.icebergcatalog.dao.repositories.{IcebergNamespaceRepository, IcebergTableRepository}
import ru.otus.icebergcatalog.services.IcebergCatalogService
import ru.otus.icebergcatalog.configuration.Configuration
import ru.otus.icebergcatalog.db.{DataSource, LiquibaseService}
import zio.blocking.Blocking
import zio.clock.Clock
import zio.random.Random
import zio._
object App {


  type AppEnvironment = IcebergCatalogService.IcebergCatalogService with
   IcebergTableRepository.IcebergTableRepository with Configuration with
   LiquibaseService.Liqui with LiquibaseService.LiquibaseService
    with Clock with Blocking
    with Random with DataSource

  val appEnvironment =  Configuration.live >+> Blocking.live >+> db.zioDS >+>
    LiquibaseService.liquibaseLayer ++ IcebergTableRepository.live >+> IcebergNamespaceRepository.live >+> IcebergCatalogService.live ++ LiquibaseService.live

  val httpApp = IcebergCatalogAPI.api


  val server = (LiquibaseService.performMigration *>
    zhttp.service.Server.start(8080, httpApp))
      .provideCustomLayer(appEnvironment)


}