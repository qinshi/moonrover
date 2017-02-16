package com.moonrover.rover

import java.util.concurrent.{TimeUnit, Executors}

import com.moonrover.Config
import com.moonrover.ground.{ReportCalculator, LocationCalculator}
import com.moonrover.metrics.{TurnMetrics, CycleMetrics, BaseMetrics}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Random

/**
 * Created by qinshi on 17/2/12.
 */

//月球车仿真类，主要功能包括：1.解析配置文件生成上报数据,2.启动一个定期线程，向信道上报数据
class Rover {

  var id:String = null

  var runningSec:Int = -1

  var delay:Int = 0

  var reportMetrics:ArrayBuffer[BaseMetrics] = new ArrayBuffer[BaseMetrics]()

  var metricsOffset = 0

  //the simulate file path
  var pathFile:String = null

  def this(idIn:String, pathFileIn:String) {
    this()
    this.id = idIn
    this.pathFile = pathFileIn

    //start moon rover in a random delay
    val randomDelay = Math.abs(Random.nextInt()) % Config.GGCRateInMs

    this.delay = randomDelay

    initMetrics()
  }

  def getNextMetrics(): ArrayBuffer[BaseMetrics] = {
    runningSec += 1
    val ret =  new ArrayBuffer[BaseMetrics]()
    while (metricsOffset < reportMetrics.length
      && reportMetrics(metricsOffset).timestamp <= runningSec * 1000) {
      ret += reportMetrics(metricsOffset)
      metricsOffset += 1
    }
    ret
  }

  def start(): Unit = {

    if(Config.isDebug) {
      println("start rover " + id + " random delay = " + delay)
    }

    Executors.newSingleThreadScheduledExecutor().
      scheduleAtFixedRate(new ReportCalculator(this), delay, Config.roverRate, TimeUnit.MILLISECONDS)
  }

  def initMetrics(): Unit = {
    val file=Source.fromFile(pathFile)
    for(line <- file.getLines)
    {
      val tokens = line.split(" ")
      if (tokens.length == 5) {
        val metrics = new CycleMetrics()
        metrics.roverId = id
        metrics.delay = delay
        metrics.timestamp = tokens(0).toInt
        metrics.direction = tokens(1).toDouble
        metrics.locationX = tokens(2).toDouble
        metrics.locationY = tokens(3).toDouble
        metrics.speed = tokens(4).toDouble
        reportMetrics += metrics
      }
      else {
        val metrics = new TurnMetrics()
        metrics.roverId = id
        metrics.delay = delay
        metrics.timestamp = tokens(0).toInt
        metrics.turnDirection = tokens(1).toInt
      }
    }
    file.close

  }


}
