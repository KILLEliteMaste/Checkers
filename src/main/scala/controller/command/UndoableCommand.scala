package controller.command

import controller.Controller

trait UndoableCommand extends Command {
  def undo(input: List[String]):String
}
