package strings

import kotlin.coroutines.experimental.buildSequence

object Arrangements {
  fun arrangementABC(n: Int) {
    val seq = buildSequence {
      var current = "a".repeat(n)
      yield(current)
      while (current != "c".repeat(n)) {
        current = increment(current)
      }
    }
//    Integer.bitCount()
  }

  fun increment(current: String): String {
    var carry = false
    return current.foldRightIndexed("") { index, c, result ->
      if (index == 0) {
        val (inc, car) = increment(c)
        carry = car
        result + inc
      }
      else {
        if (carry) {
          result + increment(c)
        }
        else {
          result + (c)
        }
      }
    }
  }

  fun increment(c: Char): Pair<Char, Boolean> =
      when (c) {
        'a' -> {
          'b' to false
        }
        'b' -> {
          'c' to false
        }
        'c' -> {
          'a' to true
        }
        else -> throw AssertionError("Can't be here")
      }
}