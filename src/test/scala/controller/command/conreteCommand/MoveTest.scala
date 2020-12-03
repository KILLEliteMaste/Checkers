package controller.command.conreteCommand

import controller.{Controller, PlayerState}
import model.Position
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MoveTest extends AnyWordSpec with Matchers {

  "MoveTest" when {
    "Origin input is valid" should {
      "2 arguments" in {
        Move.isOriginInputValid(List("1", "2")) should be(true)
      }
      "only numbers" in {
        Move.isOriginInputValid(List("6", "4")) should be(true)
      }
    }
    "Origin input is invalid" should {
      "not only numbers" in {
        Move.isOriginInputValid(List("5", "Hi")) should be(false)
      }
    }
    "Destination input is invalid" should {
      "not only numbers" in {
        Move.isDestinationInputValid(List("1", "Hi")) should be(false)
      }
      "not % 2 arguments" in {
        Move.isDestinationInputValid(List("1", "2", "7")) should be(false)
      }
    }
    "Destination input is valid" should {
      "only numbers" in {
        Move.isDestinationInputValid(List("1", "2", "7", "6", "2", "3")) should be(true)
      }
      "% 2 arguments" in {
        Move.isDestinationInputValid(List("1", "2", "7", "6")) should be(true)
      }
    }

    "ProcessInputLine" should {
      "be able to move" in {
        Controller.createNewField()
        Move.handleCommand(List("2", "1", "3", "2"))
        Controller.field.matrix.cell(3, 2).value shouldBe 1
      }
      "be able to jump twice" in {
        Controller.createNewField()
        Controller.moveFromPositionToPosition(Position(2, 5), Position(3, 4), 1, alreadyMoved = false)
        Controller.changePlayerTurn()
        Controller.moveFromPositionToPosition(Position(5, 0), Position(4, 1), 3, alreadyMoved = false)
        Controller.changePlayerTurn()
        Controller.moveFromPositionToPosition(Position(3, 4), Position(4, 3), 1, alreadyMoved = false)
        Controller.changePlayerTurn()
        Controller.moveFromPositionToPosition(Position(6, 1), Position(5, 0), 3, alreadyMoved = false)
        Controller.changePlayerTurn()
        Controller.moveFromPositionToPosition(Position(1, 6), Position(2, 5), 1, alreadyMoved = false)
        Controller.changePlayerTurn()
        Move.handleCommand(List("5", "2", "3", "4", "1", "6"))
        Controller.field.matrix.cell(1, 6).value shouldBe 3
      }
      "should not work because origin position is out of bounds" in {
        Controller.createNewField()
        val ret = Move.handleCommand(List("9", "9"))
        ret shouldEqual "The given positions are not inside the field"
      }
      "should not work because destination cell is not empty" in {
        Controller.createNewField()
        val ret = Move.handleCommand(List("0", "1", "2", "1"))
        ret shouldEqual "One destination is not empty to be able to move to this position"
      }
      "should not work because position not inside of field" in {
        Controller.createNewField()
        val ret = Move.handleCommand(List("2", "7", "3", "8"))
        ret shouldEqual "The given positions are not inside the field"
      }
      "should not work because cell not belongs to player" in {
        Controller.createNewField()
        val ret = Move.handleCommand(List("5", "0", "4", "1"))
        ret shouldEqual "Cell does not contain a stone that belongs to Player 1"
      }
      "should not work to move straight" in {
        Controller.createNewField()
        Move.handleCommand(List("2", "1", "3", "1"))
        Controller.playerState should be(PlayerState.PLAYER_1)
      }
    }
  }
}
