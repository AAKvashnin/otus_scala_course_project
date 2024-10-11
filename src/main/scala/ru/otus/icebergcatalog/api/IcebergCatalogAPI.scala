package ru.otus.icebergcatalog.api

import zhttp.http._
import zio.ZIO
import io.circe.syntax._


object IcebergCatalogAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> "!!" =>

  }
}

