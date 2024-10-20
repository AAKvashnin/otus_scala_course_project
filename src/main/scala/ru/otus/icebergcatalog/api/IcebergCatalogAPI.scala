package ru.otus.icebergcatalog.api

import zhttp.http._
import zio.ZIO
import io.circe.syntax._
import ru.otus.icebergcatalog.services.IcebergCatalogService
import ru.otus.icebergcatalog.dto.{NamespaceDTO}



object IcebergCatalogAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> !!  / "v1" / "config" =>
      IcebergCatalogService.config.foldM(
        err => ZIO.succeed(Response.status(Status.BadRequest)),
        result => ZIO.succeed(Response.json(result.asJson.toString()))
      )
    case Method.GET -> !! / "v1" / prefix / "namespaces" =>
      IcebergCatalogService.listNamespaces.foldM(
        err=>ZIO.succeed(Response.status(Status.BadRequest)),
        result=>ZIO.succeed(Response.json(result.asJson.toString()))
      )
    case req@Method.POST -> !! / "v1" / prefix / "namespaces" =>(for{
      r <- req.body
      _ <- ZIO.debug(r.asJson)
      _ <- ZIO.debug("Request: " + NamespaceDTO.decoder.decodeJson(r.asJson) )
      dto <- ZIO.fromEither(NamespaceDTO.decoder.decodeJson(r.asJson))
      result <- IcebergCatalogService.createNamespace(dto)
    } yield result).foldM(
        err=>ZIO.succeed(Response.status(Status.BadRequest)),
        result=>ZIO.succeed(Response.json(result.asJson.toString()))
      )
    case Method.GET -> !! / "v1" / prefix / "namespaces" / namespace => ???
    case Method.HEAD -> !! / "v1" / prefix / "namespaces" / namespace => ???
    case Method.DELETE -> !! / "v1" / prefix / "namespaces" / namespace => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" /  namespace / "properties" => ???
    case Method.GET -> !! / "v1" / prefix / "namespaces" / namespace / "tables" => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "tables" => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table / "plan" => ???
    case Method.GET -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table / "plan" / "plan-id" => ???
    case Method.DELETE -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table / "plan" / "plan-id" => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table / "tasks" => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "register" => ???
    case Method.GET -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table => ???
    case Method.DELETE -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table => ???
    case Method.HEAD -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table => ???
    case Method.POST -> !! / "v1" / prefix / "tables" / "rename" => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "tables" / table / "metrics" => ???
    case Method.POST -> !! / "v1" / prefix / "transactions" / "commit" => ???
    case Method.GET -> !! / "v1" / prefix / "namespaces" / namespace / "views" => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "views" => ???
    case Method.GET -> !! / "v1" / prefix / "namespaces" / namespace / "views" / view => ???
    case Method.POST -> !! / "v1" / prefix / "namespaces" / namespace / "views" / view => ???
    case Method.DELETE -> !! / "v1" / prefix / "namespaces" / namespace / "views" / view => ???
    case Method.HEAD -> !! / "v1" / prefix / "namespaces" / namespace / "views" / view => ???
    case Method.POST -> !! / "v1" / prefix / "views" => ???
  }
}

