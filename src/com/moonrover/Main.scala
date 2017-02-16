package com.moonrover

object Main {
  def main(args: Array[String]): Unit = {

    if (Config.createFile) {
      FileUtil.createFile()
    }
    else {
      Env.init()

      Env.startSimulate()
    }

  }
}