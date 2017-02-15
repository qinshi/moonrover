package com.moonrover.ground

import java.util.Date
import java.util.concurrent.{TimeUnit, ScheduledExecutorService, Executors, ExecutorService}

import com.moonrover.Config
import com.moonrover.metrics.{TurnMetrics, CycleMetrics, BaseMetrics}
import com.moonrover.rover.Rover

import scala.collection.mutable

/**
 * Created by qinshi on 17/2/12.
 */
class GroundControlCenter {

  val cycleMetricsMap = new mutable.HashMap[String, CycleMetrics]

  val fixedDirectMap = new mutable.HashMap[String, Double]

  var startTS:Long = 0


  def reportMetrics(metrics:BaseMetrics): Unit = {
    if (metrics.isInstanceOf[CycleMetrics]) {
      if(Config.isDebug) {
        println("process cycleMetrics")
      }

      cycleMetricsMap(metrics.roverId) = metrics.asInstanceOf[CycleMetrics]
      fixedDirectMap(metrics.roverId) = 0
    }

    if (metrics.isInstanceOf[TurnMetrics]) {
      if(Config.isDebug) {
        println("process trunMetrics")
      }

      fixedDirectMap(metrics.roverId) += metrics.asInstanceOf[TurnMetrics].turnDirection
    }
  }

  def start(): Unit = {
    if(Config.isDebug) {
      println("start GGC...")
    }

    val now = new Date
    startTS = now.getTime

    Executors.newSingleThreadScheduledExecutor().
      scheduleAtFixedRate(new LocationCalculator(), Config.GGCDelayInMs, Config.GGCRateInMs, TimeUnit.MILLISECONDS)
  }

}
