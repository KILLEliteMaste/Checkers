package aview

import controller.Controller.playerState
import controller.command.{Command, UndoableCommand}
import controller.command.conreteCommand.{Move, New, Quit}
import controller.{Controller, GameState, PlayerState}
import util.Observer

import scala.collection.mutable
import scala.io.StdIn._

case object TUI extends Observer {
  Controller.add(this)

  val commands = new mutable.HashMap[String, Command]()
  val undoableCommand = new mutable.HashMap[String, UndoableCommand]()

  def processInputLine(input: String): Unit = {
    val inputSplit = input.split("\\s+").toList

    commands.get(inputSplit.head).foreach(command => println(command.handleCommand(inputSplit.drop(1))))
    undoableCommand.get(inputSplit.head).foreach(command => command.undo(inputSplit.drop(1)))
  }


  def run(): Unit = {
    println("The following commands are available for the text UI:")
    println("new <Number>   | Create a new field. When a number is given as an argument the field size will be changed")
    println("quit   | Quits the game")
    println("move <X Y> <X Y X Y...>   | Move a stone <FROM> <TO> with <TO> taking multiple target positions to jump over stones")
    commands.put("new", New)
    commands.put("quit", Quit)
    //commands.put("q", Quit)
    commands.put("move", Move)
    undoableCommand.put("undo", Move)

    var input: String = ""

    TUI.update()
    Controller.gameState = GameState.RUNNING
    while (!input.equals("q")) {
      //do {
      input = readLine()
      processInputLine(input)
    }
    //} while ( || input != "quit")
    println("Game ended")
  }

  override def update(): Unit = {
    println(Controller.matrixToString)
    println(PlayerState.message(playerState))
  }
}
