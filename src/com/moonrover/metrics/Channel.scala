package com.moonrover.metrics

import com.moonrover.Env

//information channel
class Channel {
  def reportMetrics(metrics: BaseMetrics): Unit = {
    Env.groundControlCenter.reportMetrics(metrics)
  }
}