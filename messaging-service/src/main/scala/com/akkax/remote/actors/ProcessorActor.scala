package com.akkax.remote.actors

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings, ShardRegion}
import com.akkax.remote.actors.ProcessorActor.{Command, ProcessUserMessage}
import com.akkax.remote.models.UserMessage


class ProcessorActor extends Actor {

  override def receive: Receive = {
    case msg: Command => msg match {
      case pum: ProcessUserMessage =>
        println(s"Message found by actor [${self.path.name}] : " + pum)
        Thread.sleep(1000)
        sender ! Done

      case msg => println(s"Got unknown message : $msg")
    }

  }

}

object ProcessorActor {

  val numberOfShards: Long = 20
  val extractEntityId: ShardRegion.ExtractEntityId = {
    case command: Command => command.id -> command
  }
  val extractShardId: ShardRegion.ExtractShardId = {
    case command: Command => shard(command.id)
  }

  def apply(system: ActorSystem): ActorRef = {
    system.actorOf(Props[ProcessorActor])
  }

  private def shard(id: String): String = {
    math.abs(id.hashCode % numberOfShards).toString
  }

  trait Command {
    val id: String
  }

  case class ProcessUserMessage(id: String, message: UserMessage) extends Command

}


object ProcessorActorShard {

  def processorRegion(system: ActorSystem): ActorRef = ClusterSharding(system).start(
    typeName = "processor",
    entityProps = Props[ProcessorActor],
    settings = ClusterShardingSettings(system),
    extractEntityId = ProcessorActor.extractEntityId,
    extractShardId = ProcessorActor.extractShardId
  )

}

