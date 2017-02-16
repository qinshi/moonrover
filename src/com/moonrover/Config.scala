package com.moonrover

object Config {
  val isDebug = false

  //设置为false时执行仿真，设置为true时随机生成月球车的移动路径
  val createFile = false

  //ground controller center start delay
  val GGCDelayInMs = 0

  val GGCRateInMs = 500

  val roverIds = Array("1", "2", "3", "4", "5")

  val roverPathPrefix = "rover_path_"

  val roverRate = 1000
}