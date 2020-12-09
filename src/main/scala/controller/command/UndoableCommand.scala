package controller.command

import controller.Controller

trait UndoableCommand extends Command {
  def undoStep(input: List[String], controller: Controller): String

  def redoStep(input: List[String], controller: Controller): String

  def doStep(input: List[String], controller: Controller): String
}
