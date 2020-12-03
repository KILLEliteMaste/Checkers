package controller.command.conreteCommand

import controller.Controller
import controller.command.Command

case object New extends Command {
  override def handleCommand(input: List[String]): String = {
    if (input.isEmpty) {
      Controller.createNewField()
    } else {
      Controller.createNewField(input.head.toInt)
    }
    "Created a new fiel with size: " + Controller.field.fieldSize
  }
}
