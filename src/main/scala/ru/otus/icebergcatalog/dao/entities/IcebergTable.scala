package ru.otus.icebergcatalog.dao.entities

final case class IcebergTable(catalogName:String, tableNamespace:String, tableName:String, metadataLocation:String, icebergType:String)

