package ru.otus.icebergcatalog

import io.circe._
import io.circe.generic.semiauto._

import scala.util.{Failure, Success, Try}
package object dto {

 case class CatalogConfigDTO(defaults:Map[String,String],overrides:Map[String,String],endpoints:List[String])

 object CatalogConfigDTO {
  implicit val decoder: Decoder[CatalogConfigDTO] = deriveDecoder
  implicit val encoder: Encoder[CatalogConfigDTO] = deriveEncoder
 }

 case class ListNamespacesResponseDTO(namespaces:List[List[String]])

 object ListNamespacesResponseDTO {
  implicit val decoder: Decoder[ListNamespacesResponseDTO] = deriveDecoder
  implicit val encoder: Encoder[ListNamespacesResponseDTO] = deriveEncoder

 }

 case class NamespaceDTO(`namespace`:List[String], properties:Map[String,String])

 object NamespaceDTO {
  implicit val decoder: Decoder[NamespaceDTO]=deriveDecoder[NamespaceDTO]
  implicit val encoder: Encoder[NamespaceDTO]=deriveEncoder[NamespaceDTO]
 }

 case class TableIdentifierDTO(namespace:List[String],name:String)
 case class ListTablesResponseDTO(identifiers:List[TableIdentifierDTO])

 object TableIdentifierDTO {
  implicit val decoder:Decoder[TableIdentifierDTO]=deriveDecoder[TableIdentifierDTO]
  implicit val encoder:Encoder[TableIdentifierDTO]=deriveEncoder[TableIdentifierDTO]

 }

 object ListTablesResponseDTO {
  implicit val decoder:Decoder[ListTablesResponseDTO]=deriveDecoder[ListTablesResponseDTO]
  implicit val encoder:Encoder[ListTablesResponseDTO]=deriveEncoder[ListTablesResponseDTO]
 }

}
