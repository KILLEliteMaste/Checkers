package aview


import controller.Controller
import controller.command.conreteCommand.{Move, New, Quit}
import controller.command.{Command, UndoableCommand}

import scala.collection.mutable

abstract class UI extends UserInterface {
  val commands = new mutable.HashMap[String, Command]()
  val undoableCommand = new mutable.HashMap[String, UndoableCommand]()
  commands.put("new", New())
  commands.put("quit", Quit())
  val move: Move = Move()
  commands.put("move", move)
  undoableCommand.put("undo", move)
  undoableCommand.put("redo", move)

  def processInputLine(input: String, controller: Controller): Unit = {
    val inputSplit = input.toLowerCase().split("\\s+").toList

    commands.get(inputSplit.head).foreach(command => println(command.handleCommand(inputSplit.drop(1), controller)))
    undoableCommand.get(inputSplit.head).foreach(command => command.undoStep(inputSplit.drop(1), controller))
  }

  def run(): Unit
}
