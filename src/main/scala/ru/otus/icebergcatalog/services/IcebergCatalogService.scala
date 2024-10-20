package ru.otus.icebergcatalog.services

import zio.{Has, RIO, ULayer, ZIO, ZLayer}
import zio.macros.accessible
import zio.random.Random
import ru.otus.icebergcatalog.db.DataSource
import ru.otus.icebergcatalog.dto._
import ru.otus.icebergcatalog.dao.repositories.{IcebergNamespaceRepository,IcebergTableRepository}
import ru.otus.icebergcatalog.dao.entities._
import ru.otus.icebergcatalog.db



@accessible
object IcebergCatalogService {

  type IcebergCatalogService = Has[Service]

  trait Service {
    def config():RIO[db.DataSource, CatalogConfigDTO]
    def listNamespaces():RIO[db.DataSource,ListNamespacesResponseDTO]
    def createNamespace(req: NamespaceDTO):RIO[db.DataSource,NamespaceDTO]
    def listTables(namespace:String):RIO[db.DataSource,ListTablesResponseDTO]

  }

  class Impl(icebergTableRepository:IcebergTableRepository.Service, icebergNamespaceRepository:IcebergNamespaceRepository.Service) extends Service {

    val ctx  = db.Ctx

    def config():RIO[db.DataSource, CatalogConfigDTO] =
      ZIO.succeed(CatalogConfigDTO(Map.empty[String,String],Map.empty[String,String],List.empty[String]))

    def listNamespaces():RIO[db.DataSource,ListNamespacesResponseDTO]=for {
      namespaces<-icebergTableRepository.listNamespace()
      result<-RIO.succeed(ListNamespacesResponseDTO(List(namespaces)))
    } yield (result)

    def listTables(namespace:String):RIO[db.DataSource,ListTablesResponseDTO]=for{
      tables<-icebergTableRepository.find(namespace)
      result=ListTablesResponseDTO(tables.map(t=>TableIdentifierDTO(List(t.tableNamespace),t.tableName)))
    } yield result


    def createNamespace(req:NamespaceDTO):RIO[db.DataSource,NamespaceDTO]=for {
       _ <-icebergNamespaceRepository.insertNamespaceProperty(IcebergNamespaceProperty("", req.namespace.head, "exists", "true"))
       result <- ZIO.succeed(NamespaceDTO(req.namespace, Map("exists"-> "true")))
    } yield (result)

  }

  val live:ZLayer[IcebergTableRepository.IcebergTableRepository with IcebergNamespaceRepository.IcebergNamespaceRepository, Nothing, IcebergCatalogService.IcebergCatalogService]=ZLayer.fromServices[IcebergTableRepository.Service,IcebergNamespaceRepository.Service,IcebergCatalogService.Service]((repo,repo1)=>new Impl(repo,repo1))
}