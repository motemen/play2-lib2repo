package controllers

import play.api.mvc.{Action, Controller}

object Cpan extends Controller {
  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  def search(module: String) = Action.async {
    providers.Cpan.fetchRepositoryUrl(module).map {
      case None => NotFound("")
      case Some(repositoryUrl) => Redirect(repositoryUrl)
    }
  }
}
