package com.moonrover.ground

import com.moonrover.Config

/**
 * Created by qinshi on 17/2/12.
 */
class ReportCalculator extends Runnable {
  var id:String = null

  def this(idIn:String) {
    this()
    this.id = idIn
  }

  override def run(): Unit = {
    if(Config.isDebug) {
      println("run report calculator, id = " + id)
    }
  }

}