package genericsmadness

class G<T, U, V, W>(val t: T, val u: U, val v: V, val w: W)

class WithAStupidlyLongNameJustBecauseICan

fun main(args: Array<String>) {
  val x = WithAStupidlyLongNameJustBecauseICan()
  val a = G(x, x, x, x)
  val b = G(a, a, a, a)
  val c = G(b, b, b, b)

  val d = G(c,c,c,c)
}

