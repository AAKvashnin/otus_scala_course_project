package ru.otus.icebergcatalog.services

import zio.{Has, RIO, ULayer, ZIO, ZLayer}
import zio.macros.accessible
import zio.random.Random
import ru.otus.icebergcatalog.db.DataSource
import ru.otus.icebergcatalog.dto._
import ru.otus.icebergcatalog.dao.repositories.{IcebergTableRepository}
import ru.otus.icebergcatalog.dao.entities._
import ru.otus.icebergcatalog.db



@accessible
object IcebergCatalogService {

  type IcebergCatalogService = Has[Service]

  trait Service {
    def config():RIO[Any, CatalogConfigDTO]
    def listNamespaces():RIO[Any,ListNamespacesResponseDTO]
    def createNamespace(req: NamespaceDTO):RIO[Any,NamespaceDTO]

  }

  class Impl(icebergTableRepository:IcebergTableRepository.Service) extends Service {

    val ctx  = db.Ctx

    def config():RIO[Any, CatalogConfigDTO] =
      ZIO.succeed(CatalogConfigDTO(Map.empty[String,String],Map.empty[String,String],List.empty[String]))

    def listNamespaces():RIO[Any,ListNamespacesResponseDTO]=for {
      namespaces<-icebergTableRepository.listNamespace()
      namespacesList<-RIO.collectAll(namespaces)
      results<-RIO.succeed(namespacesList.map(l=>ListNamespacesResponseDTO(l)))
    } yield (results)
//      ZIO.succeed(
//        ListNamespacesResponseDTO(List.empty[List[String]])
//      )

    def createNamespace(req:NamespaceDTO):RIO[Any,NamespaceDTO]=
      ZIO.succeed(NamespaceDTO(List("example"),Map("property"-> "value")))

  }

  val live:ULayer[IcebergCatalogService]=ZLayer.succeed(new Impl(IcebergTableRepository.live))
}