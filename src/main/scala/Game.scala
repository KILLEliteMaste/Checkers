import aview.UserInterface
import controller.Controller
import model.Cell

import scala.util.{Failure, Success, Try}

case object Game {

  def main(args: Array[String]): Unit = {
    val uiType = "tui"

    Try(UserInterface(uiType, Controller())) match {
      case Failure(v) => println("Could not start UI because: " + v.getMessage)
      case Success(v) => println("GOOD BYE")
    }

  }
}

/*def divide: Try[Int] = {
  val dividend = Try(Console.readLine("Enter an Int that you'd like to divide:\n").toInt)
  val divisor = Try(Console.readLine("Enter an Int that you'd like to divide by:\n").toInt)
  val problem = dividend.flatMap(x => divisor.map(y => x/y))
  problem match {
    case Success(v) =>
      println("Result of " + dividend.get + "/"+ divisor.get +" is: " + v)
      Success(v)
    case Failure(e) =>
      println("You must've divided by zero or entered something that's not an Int. Try again!")
      println("Info from the exception: " + e.getMessage)
      divide
  }
}*/
/*
Schmeiß 2 steine gleichzeitig
2 1
3 2

5 6
4 7

3 2
4 3

4 7
3 6

1 0
2 1

5 4
3 2 1 0

 */