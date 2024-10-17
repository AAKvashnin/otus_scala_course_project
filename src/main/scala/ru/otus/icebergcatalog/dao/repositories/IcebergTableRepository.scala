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

    def listNamespace():QIO[List[String]]

    def find(namespace:String):QIO[Option[IcebergTable]]


  }

  class ServiceImpl extends Service {

   val icebergTableSchema= quote {
     querySchema[IcebergTable]("""iceberg_tables""", _.tableName -> "table_name", _.catalogName->"catalog_name", _.tableNamespace->"table_namespace", _.metadataLocation->"metadata_location", _.icebergType->"iceberg_type")
   }



    def list():QIO[List[IcebergTable]]=ctx.run(icebergTableSchema)

    def listNamespace(): QIO[List[String]] = ctx.run(icebergTableSchema.map(t=>t.tableNamespace).distinct)

    def find(namespace:String):QIO[Option[IcebergTable]]=ctx.run(icebergTableSchema.filter(_.tableNamespace == lift(namespace)).take(1)).map(_.headOption)



  }

  val live: ULayer[IcebergTableRepository] = ZLayer.succeed(new ServiceImpl)


}