package tailrec

object Golden {
  tailrec fun compute(phi: Double = 1.0): Double =
      if (phi * phi == 1 + phi) phi
      else with(phi) {
        println(this)
        compute(1 / this + 1)
      }
}
