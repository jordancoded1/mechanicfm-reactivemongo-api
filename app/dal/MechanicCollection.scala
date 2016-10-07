package dal

import play.api.libs.json.JsArray

import scala.concurrent.Future

trait MechanicCollection {
  def getAll: Future[JsArray]
}
