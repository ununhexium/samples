package presh.talwalkar

fun fact(n: Long): Long =
    if (n == 0L) 1L else n * fact(n - 1L)

/**
 * https://www.youtube.com/watch?v=gAPNzzeNWZg
 */
fun main(args: Array<String>) {
  val solutions = (1..9L).flatMap { a ->
    (1..9L).flatMap { b ->
      (1..9L).map { c ->
        (a * 100 + b * 10 + c) to (fact(a) + fact(b) + fact(c))
      }
    }
  }.filter {
    it.first == it.second
  }

  println(solutions)
}