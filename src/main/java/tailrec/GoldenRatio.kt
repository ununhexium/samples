package tailrec

object Golden {
  tailrec fun compute(phi: Double = 1.0): Double =
      if (phi * phi == 1 + phi) phi
      else compute(1 / phi + 1)
}