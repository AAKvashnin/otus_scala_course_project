package ru.otus.icebergcatalog.services

import zio.{Has, RIO, ULayer, ZIO, ZLayer}
import zio.macros.accessible
import zio.random.Random
import ru.otus.icebergcatalog.db.DataSource
import ru.otus.icebergcatalog.dto._



@accessible
object IcebergCatalogService {

  type IcebergCatalogService = Has[Service]

  trait Service {
    def config():RIO[Any, CatalogConfigDTO]
    def listNamespaces():RIO[Any,ListNamespacesResponseDTO]
    def createNamespace(req: NamespaceDTO):RIO[Any,NamespaceDTO]

  }

  class Impl extends Service {

    def config():RIO[Any, CatalogConfigDTO] =
      ZIO.succeed(CatalogConfigDTO(Map.empty[String,String],Map.empty[String,String],List.empty[String]))

    def listNamespaces():RIO[Any,ListNamespacesResponseDTO]=
      ZIO.succeed(ListNamespacesResponseDTO(List.empty[List[String]]))

    def createNamespace(req:NamespaceDTO):RIO[Any,NamespaceDTO]=
      ZIO.succeed(NamespaceDTO(List("example"),Map("property"-> "value")))

  }

  val live:ULayer[IcebergCatalogService]=ZLayer.succeed(new Impl)
}