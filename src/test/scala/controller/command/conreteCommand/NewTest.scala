package controller.command.conreteCommand

import controller.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class NewTest extends AnyWordSpec with Matchers {
  "NewTest" when {
    "create new field" should {
      "with same size" in {
        Controller.createNewField(8)
        New.handleCommand(List())
        Controller.field.fieldSize shouldBe 8
      }
      "with different size" in {
        Controller.createNewField(10)
        New.handleCommand(List("10"))
        Controller.field.fieldSize shouldBe 10
      }
    }
  }
}
