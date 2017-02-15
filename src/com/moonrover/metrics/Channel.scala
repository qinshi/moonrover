package com.moonrover.metrics

import java.util.{TimerTask, Timer}

import com.moonrover.Env

//information channel
class Channel {
  def reportMetrics(metrics: BaseMetrics): Unit = {
    val timer:Timer = new Timer()
    timer.schedule(new TimerTask {
      override def run(): Unit = {
        Env.groundControlCenter.reportMetrics(metrics)
      }
    }, 2000)
  }
}