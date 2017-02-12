package com.moonrover

import com.moonrover.ground.GroundControlCenter
import com.moonrover.metrics.Channel
import com.moonrover.rover.Rover

import scala.collection.mutable.ArrayBuffer


object Env {
  val groundControlCenter = new GroundControlCenter()
  val channel = new Channel()
  val rovers = new ArrayBuffer[Rover]()


  def init(): Unit = {
    for (id <- Config.roverIds) {
      val rover = new Rover(id, Config.roverPathPrefix + id)
      registerRover(rover)
    }
  }

  def registerRover(rover:Rover): Unit = {
    rovers += rover
  }



  def startSimulate(): Unit = {

    groundControlCenter.start()

    for (rover <- rovers) {
      rover.start()
    }

  }

}