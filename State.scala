import java.io.{FileNotFoundException, PrintWriter, UnsupportedEncodingException}

import State.length0

import scala.beans.BeanProperty

class State(@BeanProperty var player: Player, @BeanProperty var board: Board, @BeanProperty var lastMove: Move)
  extends Comparable[Any] {

  @BeanProperty
  var children: Array[State] = length0

  @BeanProperty
  var value: Int = 0

  def initializeChildren() {
    val moves = board.getPossibleMoves(player)
    for (i <- 0 to moves.length - 1) {
      var b1 = new Board(board)
      b1.makeMove(moves(i))
      val state: State = new State(player.opponent, b1, moves(i))
      children :+= state
    }
  }

  def writeToFile() {
    var writer: PrintWriter = null
    try {
      writer = new PrintWriter("output.txt", "UTF-8")
      writer.println(this)
    } catch {
      case e@(_: FileNotFoundException | _: UnsupportedEncodingException) => e.printStackTrace()
    } finally {
      writer.close();
    }
  }

  override def toString(): String = {
    println("State.toString printing")
    toStringHelper(0, "")
  }

  override def compareTo(o: Any): Int = ???

  private def toStringHelper(d: Int, ind: String): String = {
    var str = ind + player + " to play\n"
    str = str + ind + "Value: " + value + "\n"
    str = str + board.toString(ind) + "\n"
    if (children != null && children.length > 0) {
      str = str + ind + "Children at depth " + (d + 1) + ":\n" + ind +
        "----------------\n"
      for (s <- children) {
        str = str + s.toStringHelper(d + 1, ind + "   ")
      }
    }
    str
  }
}

object State {

  val length0 = Array[State]()
}

