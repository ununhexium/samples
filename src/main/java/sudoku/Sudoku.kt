package sudoku

data class Grid(val given: List<List<Int>>)

data class Guess(val position: Pair<Int,Int>, val number:Int)

data class Proposal(val original:Sudoku, val proposition: List<Guess>)

fun main(args: Array<String>) {
//  val grid1 = parse()
}

typealias Sudoku = List<List<Int>>

fun parse(text: List<String>): Sudoku {
  val parsed = text.map {
    it.trim()
  }.filter {
    it.isNotEmpty()
  }.map {
    it.filter {
      it in ('0'..'9')
    }.map {
      it.toInt() - '0'.toInt()
    }
  }

  if (parsed.size != 9) {
    throw IllegalArgumentException("The grid is not 9 lines long")
  }
  if (parsed.map { it.size }.any { it != 9 }) {
    throw IllegalArgumentException("All lines must be 9 numbers long")
  }

  return parsed
}

/**
 * Lists all the values contained in the given rectangle, bounds inclusive.
 */
fun listValues(
    sudoku: Sudoku,
    startRow: Int,
    startColumn: Int,
    endRow: Int,
    endColumn: Int
): List<Int> {
  return (startRow..endRow).flatMap { row ->
    (startColumn..endColumn).map { col ->
      sudoku[row][col]
    }
  }.filter { it != 0 }
}

fun listExistingValuesInSquare(
    sudoku: Sudoku,
    row: Int,
    column: Int
): List<Int> {
  val sRow = row / 3
  val sCol = column / 3
  return listValues(
      sudoku,
      sRow * 3,
      sCol * 3,
      (sRow + 1) * 3 - 1,
      (sCol + 1) * 3 - 1
  )
}

fun listExistingValuesInRow(sudoku: Sudoku, row: Int) =
    listValues(sudoku, row, 0, row, 8)

fun listExistingValuesInColumn(sudoku: Sudoku, column: Int) =
    listValues(sudoku, 0, column, 8, column)

fun options(sudoku: Sudoku, row: Int, column: Int): List<Int> {
  val rowValues = listExistingValuesInRow(sudoku, row)
  val columnValues = listExistingValuesInColumn(sudoku, column)
  val squareValues = listExistingValuesInSquare(sudoku, row, column)
  return (1..9).filter {
    it !in rowValues && it !in columnValues && it !in squareValues
  }
}

fun reject(origin: Sudoku, proposal: Sudoku, row: Int, col: Int): Boolean {
  val opt = options(origin, row, col)
  return proposal[row][col] !in opt
}

fun accept(proposal: Sudoku) =
    proposal.flatMap {
      it.map {
        it
      }
    }.any {
      it == 0
    }.not()

fun first(original: Sudoku) = original

fun next(proposal: Sudoku, row: Int, col: Int): Sudoku {
  val current = proposal[row][col]
  val currentPosition = row to col
  val nextPosition = when (current) {
    9 -> next(row, col)
    else -> {
      currentPosition
    }
  }
  val nextNumber = when (current) {
    9 -> 1
    else -> current + 1
  }

  // TODO: change
  return proposal
}

fun next(row: Int, col: Int) =
    when (col) {
      9 -> row + 1 to 1
      else -> row to col + 1
    }
