package com.moonrover.ground

import java.util.concurrent.{TimeUnit, ScheduledExecutorService, Executors, ExecutorService}

import com.moonrover.Config
import com.moonrover.metrics.{TurnMetrics, CycleMetrics, BaseMetrics}
import com.moonrover.rover.Rover

/**
 * Created by qinshi on 17/2/12.
 */
class GroundControlCenter {

  def registerRover(rover:Rover): Unit = {


  }

  def reportMetrics(metrics:BaseMetrics): Unit = {
    if (metrics.isInstanceOf[CycleMetrics]) {
      if(Config.isDebug) {
        println("process cycleMetrics")
      }
    }

    if (metrics.isInstanceOf[TurnMetrics]) {
      if(Config.isDebug) {
        println("process trunMetrics")
      }
    }
  }

  def start(): Unit = {
    if(Config.isDebug) {
      println("start GGC...")
    }

    Executors.newSingleThreadScheduledExecutor().
      scheduleAtFixedRate(new LocationCalculator(), Config.GGCDelayInMs, Config.GGCRateInMs, TimeUnit.MILLISECONDS)
  }

}
