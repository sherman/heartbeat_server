package org.sherman.heartbeat.server

import akka.actor.{Actor, ActorRef}
import Actor._

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 30.08.11
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */

trait HeartBeatServer extends Actor {
    override def preStart() {
        remote.start("localhost", 3344);
        remote.register("heartbeat:service", self)
    }
}

class HeartBeatServerActor extends Actor {
    def receive = {
        case _ => println("test")
    }
}

object HeartBeatServerRunner {
    def main(args: Array[String]) {
        HeartBeatServerRunner.run()
    }

    def run() {
        actorOf[HeartBeatServerActor].start()
    }
}

