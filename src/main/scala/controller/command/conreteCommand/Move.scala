package controller.command.conreteCommand

import controller.Controller
import controller.command.UndoableCommand
import model.Position

case object Move extends UndoableCommand {

  val numberRegex = "(\\d+)"
  val splitAtRegex = "\\s+"
  var statusMessage = ""

  override def handleCommand(input: List[String]): String = {
    //ORIGIN POSITION
    if (!isOriginInputValid(input)) return statusMessage
    val origin = Vector(Position(input.head.toInt, input(1).toInt))

    val checkIfAllPositionsAreInBounds = Controller.checkIfAllPositionsAreInBounds(origin, Controller.field)

    if (!checkIfAllPositionsAreInBounds) {
      return Controller.statusMessage
    }

    val checkIfAllCellsBelongToPlayer = Controller.checkIfAllCellsBelongToPlayer(Controller.playerState, Controller.field, origin)
    if (!checkIfAllCellsBelongToPlayer) {
      return Controller.statusMessage
    }
    statusMessage = "MOVE FROM: " + input.head + " " + input(1) + " to:"

    //DESTINATION POSITIONS

    val destinationInput = input.drop(2)
    if (!isDestinationInputValid(destinationInput)) return statusMessage

    val vectorBuilder = Vector.newBuilder[Position]
    for (elem <- destinationInput.sliding(2, 2)) {
      vectorBuilder.+=(Position(elem.head.toInt, elem(1).toInt))
    }
    val destinations = vectorBuilder.result()


    val checkIfAllPositionsAreInBoundsDestinations = Controller.checkIfAllPositionsAreInBounds(destinations, Controller.field)
    if (!checkIfAllPositionsAreInBoundsDestinations) {
      return Controller.statusMessage
    }

    //Has to be empty otherwise you cannot move to this position
    val checkIfAllCellsAreEmpty = Controller.checkIfAllCellsAreEmpty(Controller.field, destinations)
    if (!checkIfAllCellsAreEmpty) {
      return Controller.statusMessage
    }

    //Will always be executed as it the least amount you want to jump
    val m1 = Controller.field.matrix.toString
    Controller.moveFromPositionToPosition(origin(0), destinations(0), Controller.field.matrix.cell(origin(0).x, origin(0).y).value, alreadyMoved = false)
    if (m1.equals(Controller.field.matrix.toString))
      return "Could not execute move"

    if (destinations.size != 1) {
      for (elem <- destinations.sliding(2, 1)) {
        Controller.moveFromPositionToPosition(elem(0), elem(1), Controller.field.matrix.cell(elem(0).x, elem(0).y).value, alreadyMoved = true)
      }
    }

    Controller.changePlayerTurn()
    statusMessage += "\n" + "TO: " + destinationInput.head + "   " + destinationInput(1)
    statusMessage
  }

  override def undo(input: List[String]): String = {
    "UNDO the last move"
  }

  def isDestinationInputValid(input: List[String]): Boolean = {
    if (input.size % 2 == 0) {
      if (allStringsMatchNumber(input))
        return true
    } else {
      statusMessage = "Wrong amount of arguments for the destination position"
    }
    false
  }

  def isOriginInputValid(input: List[String]): Boolean = {
    if (input.size >= 2) {
      if (allStringsMatchNumber(input))
        return true
    }
    false
  }

  def allStringsMatchNumber(list: List[String]): Boolean = {
    for (elem <- list) {
      if (!elem.matches(numberRegex)) {
        statusMessage = "One or more arguments do not match a number"
        return false
      }
    }
    true
  }
}
