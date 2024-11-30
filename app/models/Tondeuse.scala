package models

case class Tondeuse(var x: Int, var y: Int, var orientation: Char) {

  // List to store the position and orientation after each move
  private var positions: List[(Int, Int, Char)] = List((x, y, orientation))

  val directions = Array('N', 'E', 'S', 'W')
  val mouvements = Map(
    'N' -> (0, 1),
    'E' -> (1, 0),
    'S' -> (0, -1),
    'W' -> (-1, 0)
  )

  if (!directions.contains(orientation)) {
    throw new IllegalArgumentException(s"Invalid orientation: $orientation. Only 'N', 'W', 'E', 'S' are allowed.")
  }

  def tourner(direction: Char): Unit = {
    val idx = directions.indexOf(orientation)
    orientation = direction match {
      case 'G' => directions((idx - 1 + directions.length) % directions.length)
      case 'D' => directions((idx + 1) % directions.length)
      case _ =>
        throw new IllegalArgumentException(s"Invalid command: $direction. Only 'D' and 'G' are allowed.") // Exception for invalid commands
    }
    positions = positions :+ (x, y, orientation) // Track position and orientation after turning
  }

  def avancer(maxX: Int, maxY: Int): Unit = {
    val (dx, dy) = mouvements(orientation)
    val newX = x + dx
    val newY = y + dy
    if (newX >= 0 && newX <= maxX && newY >= 0 && newY <= maxY) {
      x = newX
      y = newY
      positions = positions :+ (x, y, orientation) // Add new position and orientation to the list
    }
    else {
      throw new IllegalArgumentException(s"The position ($newX, $newY) is invalid")
    }
  }

  def executerCommandes(commandes: String, maxX: Int, maxY: Int): List[(Int, Int, Char)] = {
    if(x > maxX || y > maxY || x < 0 || y < 0){
      throw new IllegalArgumentException(s"($x, $y) is out of the grid ($maxX, $maxY)")
    }
    for (commande <- commandes) {
      commande match {
        case 'G' | 'D' => tourner(commande) // Handle valid commands
        case 'A' => avancer(maxX, maxY)     // Handle valid commands
        case _ => 
          throw new IllegalArgumentException(s"Invalid command: $commande. Only 'A', 'D', and 'G' are allowed.") // Exception for invalid commands
      }
    }
    positions // Return the list of all positions and orientations
  }

  override def toString: String = s"$x $y $orientation"
}
