package ru.otus.icebergcatalog.api

import zhttp.http._
import zio.ZIO
import io.circe.syntax._
import ru.otus.icebergcatalog.services.IcebergCatalogService
import ru.otus.icebergcatalog.dto._


object IcebergCatalogAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> !!  / v1 / config =>
      IcebergCatalogService.config.foldM(
        err => ZIO.succeed(Response.status(Status.BadRequest)),
        result => ZIO.succeed(Response.json(result.asJson.toString()))
      )
    case Method.GET -> !! / v1 / prefix / namespaces =>
      IcebergCatalogService.listNamespaces.foldM(
        err=>ZIO.succeed(Response.status(Status.BadRequest)),
        result=>ZIO.succeed(Response.json(result.asJson.toString()))
      )
    case req@Method.POST -> !! / v1 / prefix / namespaces =>(for{
      r <- req.body
      dto <- ZIO.fromEither(NamespaceDTO.decoder.decodeJson(r.asJson))
      result <- IcebergCatalogService.createNamespace(dto)
    } yield result
    ).foldM(
        err=>ZIO.succeed(Response.status(Status.BadRequest)),
        result=>ZIO.succeed(Response.json(result.asJson.toString()))
      )
  }
}

