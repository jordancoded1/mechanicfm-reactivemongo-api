package dal

import com.google.inject.Singleton
import play.api.inject.Injector
import play.modules.reactivemongo.ReactiveMongoComponents
import reactivemongo.api.ReadPreference
import reactivemongo.play.json.collection.JSONCollection

import scala.util.parsing.json.JSONArray
import scala.util.{Failure, Success, Try}

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

class Category @Inject()(implicit val reactiveMongoApi: ReactiveMongoApi) extends MechanicCollection {

  private def collection: Future[JSONCollection] = {
    reactiveMongoApi.database.map(_.collection[JSONCollection]("categories"))
  }

  def getAll: Future[JsArray] = {
    collection flatMap { c: JSONCollection =>
      val cursor: Cursor[JsObject] = c.find(Json.obj()).cursor[JsObject](ReadPreference.primary)
      val list = cursor.collect[List]()
      list map { l => Json.arr(l) }
    }
  }

  /*
  val cursor: Cursor[JsObject] = collection.
    find(Json.obj("name" -> "")).
    sort(Json.obj("created" -> -1)).
    cursor[JsObject]

  // gather all the JsObjects in a list
  val futurePersonsList: Future[List[JsObject]] = cursor.collect[List]()

  // transform the list into a JsArray
  val futurePersonsJsonArray: Future[JsArray] =
  futurePersonsList.map { persons => Json.arr(persons) }

  // everything's ok! Let's reply with the array
  futurePersonsJsonArray.map { persons =>
    Ok(persons)
  }
  */
}
