package sudoku

import com.google.common.io.Resources
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SudokuKtTest {
  companion object {
    val s01a: Sudoku = listOf(
        listOf(0, 4, 0, 0, 0, 0, 1, 7, 9),
        listOf(0, 0, 2, 0, 0, 8, 0, 5, 4),
        listOf(0, 0, 6, 0, 0, 5, 0, 0, 8),
        listOf(0, 8, 0, 0, 7, 0, 9, 1, 0),
        listOf(0, 5, 0, 0, 9, 0, 0, 3, 0),
        listOf(0, 1, 9, 0, 6, 0, 0, 4, 0),
        listOf(3, 0, 0, 4, 0, 0, 7, 0, 0),
        listOf(5, 7, 0, 1, 0, 0, 2, 0, 0),
        listOf(9, 2, 8, 0, 0, 0, 0, 6, 0)
    )
  }

  @Test
  fun `can parse a sudoku file`() {
    val text = Resources.readLines(
        Resources.getResource("sudoku/s01a.txt"),
        Charsets.UTF_8
    )

    val parsed = parse(text)

    assertThat(parsed).isEqualTo(s01a)
  }

  @Test
  fun `can list the values in a given square`() {
    val square00 = listValues(s01a, 0, 0, 2, 2)
    assertThat(square00).containsAll(listOf(4, 2, 6))

    val row6 = listValues(s01a, 6, 0, 6, 8)
    assertThat(row6).containsAll(listOf(3, 4, 7))
  }

  @Test
  fun `can list the possible values in the square at the given position`() {
    val inSquare11 = listExistingValuesInSquare(s01a, 5, 5)
    assertThat(inSquare11).containsAll(listOf(6, 7, 9))

    val inSquare20 = listExistingValuesInSquare(s01a, 8, 0)
    assertThat(inSquare20).containsAll(listOf(2, 3, 5, 7, 8, 9))
  }

  @Test
  fun `can list possible numbers at a given location`() {
    val options = options(s01a, 0, 0)
    assertThat(options).containsAll(listOf(8))
  }

  @Test
  fun `can reject invalid proposition`() {
    assertThat(reject(s01a, listOf(listOf(1)), 0, 0)).isTrue()
  }

  @Test
  fun `can not reject valid proposition`() {
    assertThat(reject(s01a, listOf(listOf(8)), 0, 0)).isFalse()
  }

  @Test
  fun `can accept a complete sudoku grid`() {
    /**
     * This proposal is invalid but we don't care as validity is
     * checked first in the backtracking algorithm.
     * Plus I'm too lazy to solve this grid right now.
     */
    val proposal = s01a.map {
      it.map {
        if (it == 0) 1 else it
      }
    }

    assertThat(accept(s01a)).isFalse()
    assertThat(accept(proposal)).isTrue()
  }
}

