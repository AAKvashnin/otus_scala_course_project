package ru.otus.icebergcatalog.services

import zio.{Has, RIO, ZIO, ZLayer}
import zio.macros.accessible
import zio.random.Random


@accessible
object IcebergCatalogService {

  type IcebergCatalogService = Has[Service]

  trait Service {


  }

  class Impl

}