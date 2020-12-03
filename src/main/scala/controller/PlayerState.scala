package controller

case object PlayerState extends Enumeration {
  type PlayerState = Value
  val PLAYER_1, PLAYER_2 = Value

  val map: Map[PlayerState, String] = Map[PlayerState, String](
    PLAYER_1 -> "It's player 1 turn",
    PLAYER_2 -> "It's player 2 turn"
  )

  def message(playerState: PlayerState): String = {
    map(playerState)
  }
}
