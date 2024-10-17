package ru.otus.icebergcatalog.dao.repositories

import io.getquill.context.ZioJdbc._
import zio.{Has, ULayer, ZLayer}
import io.getquill.{EntityQuery, Ord, Quoted}
import ru.otus.icebergcatalog.db
import ru.otus.icebergcatalog.dao.entities._


object IcebergNamespaceRepository {

  val ctx = db.Ctx

  import ctx._

  type IcebergNamespaceRepository = Has[Service]

  trait Service {

    def insertNamespaceProperty(namespaceProperty: IcebergNamespaceProperty):QIO[Unit]


  }

  class ServiceImpl extends Service {

    val icebergNamespacePropertySchema = quote {
      querySchema[IcebergNamespaceProperty]("""iceberg_namespace_properties""", _.namespace -> "namespace", _.catalogName -> "catalog_name", _.propertyKey -> "property_key", _.propertyValue -> "property_value")
    }

    override def insertNamespaceProperty(namespaceProperty: IcebergNamespaceProperty): QIO[Unit] = ctx.run(icebergNamespacePropertySchema.insert(lift(namespaceProperty))).unit


  }

  val live: ULayer[IcebergNamespaceRepository] = ZLayer.succeed(new ServiceImpl)



}
