package com.moonrover.ground

import java.util.Date

import com.moonrover.Config
import com.moonrover.Env
import com.moonrover.metrics.{TurnMetrics, CycleMetrics, BaseMetrics}
import com.moonrover.rover.Rover

/**
 * Created by qinshi on 17/2/12.
 */
//地面站结果打印类
class LocationCalculator extends Runnable {

  override def run(): Unit = {
    if(Config.isDebug) {
      println("run location calculator...")
    }

    val now = new Date()
    val diffTime = now.getTime - Env.groundControlCenter.startTS
    println("GGC report, current time = " + diffTime + "===============================")

    for ((id, metrics) <- Env.groundControlCenter.cycleMetricsMap) {
      val roverTimeDiff = diffTime - metrics.timestamp - metrics.delay

      var report = "rover " + id
      var reportTime = metrics.timestamp + metrics.delay
      report += ":report time = " + reportTime
      report += ",locationX = " + metrics.locationX
      report += ",locationY = " + metrics.locationY
      val fixedDirection = metrics.direction - Env.groundControlCenter.fixedDirectMap(id)
      report += ",direction = " + fixedDirection
      report += ",speed = " + metrics.speed

      val calculateX = Math.cos(metrics.direction * Math.PI / 180) * metrics.speed * roverTimeDiff / 1000 + metrics.locationX
      val calculateY = Math.sin(metrics.direction * Math.PI / 180) * metrics.speed * roverTimeDiff / 1000 + metrics.locationY

      report += ",calculateX = " + calculateX
      report += ",calculateY = " + calculateY

      println(report)

    }
    println("GGC report end===============================")
  }

}