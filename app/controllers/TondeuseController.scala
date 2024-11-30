package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.Tondeuse

@Singleton
class TondeuseController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Custom Reads for Char
  implicit val charReads: Reads[Char] = Reads[Char] { json =>
    json.validate[String].flatMap {
      case s if s.length == 1 => JsSuccess(s.charAt(0))
      case _ => JsError("Expected a single character")
    }
  }

  // Define Reads for TondeuseInput using the custom charReads
  case class TondeuseInput(x: Int, y: Int, orientation: Char, commands: String)
  implicit val tondeuseInputReads: Reads[TondeuseInput] = Json.reads[TondeuseInput]

  // Method to handle multiple Tondeuse commands
  def processMultiple = Action(parse.json) { implicit request =>
    try {
      val maxX = (request.body \ "maxX").as[Int]
      val maxY = (request.body \ "maxY").as[Int]
      val tondeuses = (request.body \ "tondeuses").as[Seq[TondeuseInput]]

      // Process each Tondeuse entry and collect each step's position or error
      val results = tondeuses.zipWithIndex.map { case (input, index) =>
        try {
          val tondeuse = Tondeuse(input.x, input.y, input.orientation)
          val positions = tondeuse.executerCommandes(input.commands, maxX, maxY)
          Json.obj(
            "tondeuse" -> s"Tondeuse ${index + 1}",
            "positions" -> positions.map { case (x, y, orientation) =>
              Json.obj("x" -> x, "y" -> y, "orientation" -> orientation.toString)
            }
          )
        } catch {
          case e: IllegalArgumentException =>
            Json.obj(
              "tondeuse" -> s"Tondeuse ${index + 1}",
              "error" -> e.getMessage // Send error message in the JSON response
            )
        }
      }

      Ok(Json.toJson(results))
    } catch {
      case e: JsResultException =>
        BadRequest(Json.obj("error" -> "Invalid JSON format", "details" -> e.getMessage))
      case e: Exception =>
        InternalServerError(Json.obj("error" -> "Unexpected server error", "details" -> e.getMessage))
    }
  }


  def index = Action { implicit request =>
    Ok(views.html.index(5, 5, Seq.empty, List("Please enter Tondeuse data to start.")))
  }
}
