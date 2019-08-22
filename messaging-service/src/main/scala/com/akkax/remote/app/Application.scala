package com.akkax.remote.app

import akka.actor.{ActorRef, ActorSelection, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.akkax.logger.Logging
import com.akkax.remote.actors.ProcessorActorShard
import com.akkax.remote.controller.Messaging
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by girish on 15/1/18.
  */
object Application extends Messaging with Logging {

  val config: Config = ConfigFactory.load
  val app: String = config.getString("application.name")
  val port: Int = config.getInt("application.port")

  info("Application conf......" + app)

  override implicit val system: ActorSystem = ActorSystem(app)

  // Sharded actor region
  val processor: ActorRef = ProcessorActorShard.processorRegion(system)

  def main(args: Array[String]): Unit = {
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(route, "localhost", port)
    println(s"Server online at http://localhost:8081")
  }
}
