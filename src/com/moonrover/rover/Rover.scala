package com.moonrover.rover

import java.util.concurrent.{TimeUnit, Executors}

import com.moonrover.Config
import com.moonrover.ground.{ReportCalculator, LocationCalculator}
import com.moonrover.metrics.{CycleMetrics, BaseMetrics}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by qinshi on 17/2/12.
 */
class Rover {

  var id:String = null

  var runningSec:Int = -1

  var delay:Int = 0

  //the simulate file path
  var pathFile:String = null

  def this(idIn:String, pathFileIn:String) {
    this()
    this.id = idIn
    this.pathFile = pathFileIn
  }

  def getNextMetrics(): ArrayBuffer[BaseMetrics] = {
    runningSec += 1
    val ret =  new ArrayBuffer[BaseMetrics]()
    var metrics = new CycleMetrics()
    metrics.timestamp = runningSec * 1000
    metrics.roverId = id
    metrics.delay = delay
    metrics.speed = 10
    metrics.direction = 45
    ret += metrics
    return ret
  }

  def start(): Unit = {

    //start moon rover in a random delay
    val randomDelay = Math.abs(Random.nextInt()) % Config.GGCRateInMs

    this.delay = randomDelay

    if(Config.isDebug) {
      println("start rover " + id + " random delay = " + randomDelay)
    }

    Executors.newSingleThreadScheduledExecutor().
      scheduleAtFixedRate(new ReportCalculator(this), randomDelay, Config.roverRate, TimeUnit.MILLISECONDS)
  }


}
