import scala.scalajs.js
import js.annotation.JSExportTopLevel
import js.annotation.JSExport
import org.scalajs.dom.raw._

import org.scalajs.dom

import upickle.default._
import shared.Protocol
import P5VectorExt._

@JSExportTopLevel("Pong")
class Pong() extends js.Object {
  val sketch: js.Function1[Sketch, Unit] = { s =>
    import s._
    val ballDiameter = 15.0
    val ballRadi = ballDiameter / 2
    var location = P5Vector(150, 150)
    var speed = P5Vector(0,  0)

    var brickHeight = 100
    var brickWidth = 15
    var brickX = 100
    var brickY = 0
    var brickBounceX = brickX + brickWidth + ballRadi

    var gameMode = 0
    var welcomeScreen = 0
    var gameScreen = 1
    var gameOverScreen = 2

    var points = 0

    setup = { () =>
      createCanvas(800, 400)
    }
    
    draw = { () =>
      background(51)
      textSize(30)
      text("Points ", 300, 40)
      text(points.toString, 410, 40)

      if (gameMode == welcomeScreen ) {
        drawWelcomeScreen()
      }else if (gameMode == gameScreen ) {
        drawGameScreen()
      }else if (gameMode == gameOverScreen ) {
        drawGameOverScreen()
      }
    }

    mouseClicked = { () =>
      if (gameMode == welcomeScreen || gameMode == gameOverScreen){
        // reset game
        location = P5Vector(150, 150)
        speed = P5Vector(10,  6.5)
        gameMode = gameScreen
      }
    }

    def drawWelcomeScreen() = {
      background(220)
      textSize(50)
      text("Press mouse to start game ", 70, 140)
    }

    def drawGameOverScreen() = {
        background(220)
        textSize(50)
        text("Game Over", 270, 140)
      }

    def drawGameScreen() = {
      if (mouseY < 100) {
        brickY = 0
      }
      else if (mouseY < 200) {
        brickY = 100
      }
      else if (mouseY < 300) {
        brickY = 200
      }
      else if (mouseY >= 300) {
        brickY = 300
      }
      
      rect(brickX, brickY, brickWidth, brickHeight);
      
      location = location + speed

      // Out of bounce
      if (location.x < brickX) then {
        gameMode = gameOverScreen
      }
      // Border bounce
      if (location.x + ballRadi > width) then {
        speed = speed.negX()
      }
      if (location.y + ballRadi > height) || (location.y < ballRadi) then {
        speed = speed.negY()
      }
      // Brick bounce
      if (location.x < brickBounceX)
      {
        if (location.y < brickY + brickHeight & location.y > brickY) then {
          speed = speed.negX()
          points += 1
        }
      }
      fill(128, 245, 128)
      ellipse(location.x, location.y, ballDiameter, ballDiameter) 

      textSize(20)
      text("Pos X " + location.x.toString, 120, 380)
      text("Pos Y " + location.y.toString, 250, 380)
      text("Brick X " + brickX.toString, 380, 380)
      text("Brick Y " + brickY.toString, 520, 380)
    } 
  }
}
/*
Class Brick(xc: Int, yc: Int, hc: Int, wc: Int) {
  var x: Int = x
  var y: Int = yc
  var h: Int = hc
  var w: Int = wc

  def setPosition(dx: Int, dy: Int) = {
      x = dx
      y = dy

      println ("Point x location : " + x)
      println ("Point y location : " + y)
  }
}
*/

