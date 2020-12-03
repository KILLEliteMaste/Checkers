package controller.command.conreteCommand

import controller.command.Command

case object Quit extends Command {
  override def handleCommand(input: List[String]): String = {
    System.exit(0)
    "BYE"
  }
}
