package com.moonrover

import java.io.{File, PrintWriter}

import scala.util.Random


object FileUtil {
  def createFile(): Unit = {
    for (id <- Config.roverIds) {
      var timestamp = 1000
      var direction = Math.abs(Random.nextInt()) % 360
      var locationX:Double = Math.abs(Random.nextInt()) % 1000
      var locationY:Double = Math.abs(Random.nextInt()) % 1000
      var speed = Math.abs(Random.nextInt()) % 10 + 10
      val writer = new PrintWriter(new File(Config.roverPathPrefix + id))

      var fixDirection = 0

      while (timestamp < 1000000) {
        locationX = Math.cos(direction * Math.PI / 180) * speed  + locationX
        locationY = Math.sin(direction * Math.PI / 180) * speed  + locationY
        direction = direction - fixDirection

        fixDirection = 0
        speed = speed + Random.nextInt() % 2
        if (speed < 5) {
          speed = 5
        }

        val line = timestamp + " " + direction + " " + locationX + " " + locationY + " " + speed + "\n"
        writer.append(line)

        //十分之一的概率进行转向
        if (Math.abs(Random.nextInt()) % 100 < 10) {
          fixDirection = Random.nextInt() % 10
          if (fixDirection != 0) {
            val tempTS = timestamp + Math.abs(Random.nextInt()) % 1000
            val tempLine = tempTS + " " + fixDirection + "\n"
            writer.append(tempLine)
          }

        }

        timestamp += 1000
      }

      writer.close()
    }
  }
}