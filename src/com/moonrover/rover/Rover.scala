package com.moonrover.rover

import java.util.concurrent.{TimeUnit, Executors}

import com.moonrover.Config
import com.moonrover.ground.{ReportCalculator, LocationCalculator}

import scala.util.Random

/**
 * Created by qinshi on 17/2/12.
 */
class Rover {

  var id:String = null

  //the simulate file path
  var pathFile:String = null

  def this(idIn:String, pathFileIn:String) {
    this()
    this.id = idIn
    this.pathFile = pathFileIn
  }

  def start(): Unit = {

    //start moon rover in a random delay
    val randomDelay = Math.abs(Random.nextInt()) % Config.GGCRateInMs

    if(Config.isDebug) {
      println("start rover " + id + " random delay = " + randomDelay)
    }

    Executors.newSingleThreadScheduledExecutor().
      scheduleAtFixedRate(new ReportCalculator(id), randomDelay, Config.roverRate, TimeUnit.MILLISECONDS)
  }


}
