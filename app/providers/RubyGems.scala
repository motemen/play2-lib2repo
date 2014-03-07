package providers

import play.api.libs.ws.WS
import play.api.libs.json.JsValue
import scala.concurrent.Future
import scala.util.control.Exception.allCatch

object RubyGems {
  val API_ENDPOINT_FORMAT = "https://rubygems.org/api/v1/gems/%s.json"

  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  def fetchJson(gem: String): Future[Either[Throwable,JsValue]] = {
    for ( res <- WS.url(API_ENDPOINT_FORMAT format gem).get() ) yield {
      allCatch.either { res.json }
    }
  }

  def fetchRepositoryUrl(gem: String): Future[Either[Throwable,String]] = {
    for ( errOrJson <- fetchJson(gem) ) yield {
      errOrJson.right.map { json =>
        allCatch.either { (json \ "source_code_uri").as[String] }
      }.joinRight
    }
  }
}
