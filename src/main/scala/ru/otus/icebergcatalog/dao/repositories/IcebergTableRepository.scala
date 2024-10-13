package ru.otus.icebergcatalog.dao.repositories

import io.getquill.context.ZioJdbc._
import zio.{Has, ULayer, ZLayer}
import io.getquill.{EntityQuery, Ord, Quoted}
import ru.otus.icebergcatalog.db


import ru.otus.icebergcatalog.dao.entities._

object IcebergTableRepository {

  val ctx = db.Ctx
  import ctx._

  type IcebergTableRepository = Has[Service]

  trait Service {

    def list():QIO[List[IcebergTable]]


  }

  class ServiceImpl extends Service {

   val icebergTableSchema= quote {
     querySchema[IcebergTable]("""iceberg_tables""", _.tableName -> "table_name", _.catalogName->"catalog_name", _.tableNamespace->"table_namespace", _.metadataLocation->"metadata_location", _.icebergType->"iceberg_type")
   }

    def list():QIO[List[IcebergTable]]=ctx.run(icebergTableSchema)



  }

  val live: ULayer[IcebergTableRepository] = ZLayer.succeed(new ServiceImpl)


}