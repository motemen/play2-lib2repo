package controllers

import play.api.mvc.{Action, Controller}

object Gem extends Controller {
  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  def search(gem: String) = Action.async {
    providers.RubyGems.fetchRepositoryUrl(gem).map {
      case Left(_) => NotFound("")
      case Right(repositoryUrl) => Redirect(repositoryUrl)
    }
  }
}
