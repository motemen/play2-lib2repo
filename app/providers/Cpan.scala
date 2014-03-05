package providers

import play.api.libs.ws.WS
import scala.concurrent.Future
import play.api.libs.json.JsValue

object Cpan {
  val API_FORMAT = "http://api.metacpan.org/v0/module/%s?join=release"

  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  def fetchModuleJson(module: String): Future[JsValue] = {
    WS.url(API_FORMAT format module).get().map(_.json)
  }

  def fetchRepositoryUrl(module: String): Future[Option[String]] = {
    fetchModuleJson(module).map { json =>
      ( json \ "release" \ "_source" \ "resources" \ "repository" \ "url" ).asOpt[String]
    }
  }
}
