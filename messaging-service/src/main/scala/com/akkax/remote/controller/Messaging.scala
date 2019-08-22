package com.akkax.remote.controller

import akka.Done
import akka.actor.{ActorRef, ActorSelection, ActorSystem}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives.{complete, path, pathPrefix, _}
import akka.http.scaladsl.server.{PathMatchers, RequestContext, Route}
import akka.pattern.ask
import akka.util.Timeout
import com.akkax.json.JsonHelper
import com.akkax.logger.Logging
import com.akkax.remote.actors.ProcessorActor.ProcessUserMessage
import com.akkax.remote.models.UserMessage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.control.NonFatal

trait Messaging extends Logging with JsonHelper {

  implicit val system: ActorSystem
  implicit val timeout: Timeout = Timeout(5 seconds)
  val processor: ActorRef

  val route: Route =
    post {
      pathPrefix("messaging") {
        path("send" / IntNumber) { senderId: Int =>
          entity(as[String]) { userMsgJsonString =>
            complete {
              sendMessageToReceiver(senderId, userMsgJsonString)
            }
          }
        }
      }
    }

  def sendMessageToReceiver(senderId: Int, userMessageJsonString: String): Future[HttpResponse] = {
    info(s"Controller: got message ...... $senderId, $userMessageJsonString")
    val userMessage = parse(userMessageJsonString).extract[UserMessage]
    (processor ? ProcessUserMessage(senderId.toString, userMessage) )
      .mapTo[Done]
      .map { res =>
        info(s"Responding with: ${res.toString}")
        HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, res.toString))
      }.recover {
      case NonFatal(ex) =>
        HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, ex.getMessage))
    }
  }

}
