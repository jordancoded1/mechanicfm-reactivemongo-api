package services

import dal.{Brand, Category, MechanicCollection}
import play.api.libs.json.JsArray
import play.modules.reactivemongo.ReactiveMongoApi
import services.Constants._

import scala.concurrent.Future

case class ApiServiceIllegalRequestException(error: String) extends Exception(error)

class ApiService(val requestedValue: String,
                 val requestType: String)(implicit reactiveMongoApi: ReactiveMongoApi) {

  lazy val dao = getDAO

  def getDAO: MechanicCollection = {
    requestedValue match {
      case CATEGORY => new Category()
      case BRAND => new Brand()
      case _ => throw ApiServiceIllegalRequestException("Invalid requested value")
    }
  }

  def result: Future[JsArray] = {
    dao.getAll
  }

}

object ApiService {
  def apply(requestedValue: String, requestType: String)(implicit reactiveMongoApi: ReactiveMongoApi): ApiService = {
    new ApiService(requestedValue, requestType)
  }
}
