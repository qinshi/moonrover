package com.moonrover.ground

import java.util.{TimerTask, Timer}

import com.moonrover.{Env, Config}
import com.moonrover.metrics.BaseMetrics
import com.moonrover.rover.Rover

import scala.collection.mutable.ArrayBuffer

/**
 * Created by qinshi on 17/2/12.
 */
class ReportCalculator extends Runnable {
  var rover:Rover = null

  def this(roverIn:Rover) {
    this()
    this.rover = roverIn
  }

  override def run(): Unit = {
    if(Config.isDebug) {
      println("run report calculator, id = " + rover.id)
    }

    val reportMetrics = rover.getNextMetrics()
    for (metrics<-reportMetrics) {
      val delay = metrics.timestamp - rover.runningSec * 1000

      if (delay < 0 || delay > 1000) {
        println("[ERROR]delay in wrong range, delay = " + delay)
      }

      if (delay == 0) {
        Env.channel.reportMetrics(metrics)
      }
      else {
        val timer:Timer = new Timer()
        timer.schedule(new TimerTask {
          override def run(): Unit = {
            Env.channel.reportMetrics(metrics)
          }
        }, delay)
      }


    }
  }

}