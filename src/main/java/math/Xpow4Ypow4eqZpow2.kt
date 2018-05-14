package math

import java.lang.Math.pow

fun main(args: Array<String>)
{
    var smallestRatioError = Double.MAX_VALUE
    var triple = Triple(0L, 0L, 0L)

    val MAX = 100L


    (1..MAX).forEach { x ->
        (1..MAX).forEach { y ->
            (1..pow(MAX.d, 2.0).L).forEachZ { z ->
                val ratio = (pow(x.d, 4.0) + pow(y.d, 4.0)) / pow(z.d, 2.0)

                val gap = if (ratio < 1.0)
                {
                    1.0 / ratio
                } else ratio

                if (gap < smallestRatioError)
                {
                    println("Found $x $y $z")
                    smallestRatioError = gap
                    triple = Triple(x, y, z)
                }

                if (ratio < 1.0)
                {
                    return@forEachZ
                }
            }
        }
    }

    println(triple)
    println("gap = $smallestRatioError")
}

private fun LongRange.forEachZ(call: (Long) -> Unit)
{
    this.forEach(call)
}

private val Long.d: Double
    get() = this.toDouble()



private val Double.L: Long
    get() = this.toLong()

