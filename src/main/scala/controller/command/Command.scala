package controller.command

trait Command {
  def handleCommand(input: List[String]): String
}
