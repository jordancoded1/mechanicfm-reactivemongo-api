package dal

import reactivemongo.api.ReadPreference
import reactivemongo.play.json.collection.JSONCollection

// Reactive Mongo imports
import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

import scala.concurrent.Future

// Reactive Mongo imports
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor

// BSON-JSON conversions/collection
import reactivemongo.play.json._

class Brand @Inject()(implicit val reactiveMongoApi: ReactiveMongoApi) extends MechanicCollection {

  private def collection: Future[JSONCollection] = {
    reactiveMongoApi.database.map(_.collection[JSONCollection]("brands"))
  }

  def getAll: Future[JsArray] = {
    collection flatMap { c: JSONCollection =>
      val cursor: Cursor[JsObject] = c.find(Json.obj()).cursor[JsObject](ReadPreference.primary)
      val list = cursor.collect[List]()
      list map { l => Json.arr(l) }
    }
  }

}
