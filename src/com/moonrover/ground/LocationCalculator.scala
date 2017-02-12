package com.moonrover.ground

import com.moonrover.Config
import com.moonrover.metrics.{TurnMetrics, CycleMetrics, BaseMetrics}
import com.moonrover.rover.Rover

/**
 * Created by qinshi on 17/2/12.
 */
class LocationCalculator extends Runnable {

  override def run(): Unit = {
    if(Config.isDebug) {
      println("run location calculator...")
    }
  }

}