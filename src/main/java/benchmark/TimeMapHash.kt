package benchmark

import net.lab0.tools.timer.TimeIt
import java.time.Duration
import java.time.temporal.ChronoUnit


val string = "dfgjlsgflasgfagsfasfgsdfpuyssderyugasefruyasjygdfi"
val string2 = string.repeat(2)
val hashMap = string.toList().groupBy { it }
val hashMap2 = string2.toList().groupBy { it }

fun main(args: Array<String>) {
    val N = 1000
    val warmup = Duration.of(1, ChronoUnit.SECONDS)

    val mapHash = TimeIt {
        hashMap.hashCode()
    }.autoTimeRepeat(N, warmup)
    println("Map #: $mapHash")

    val strHash = TimeIt {
        string.hashCode()
        }.autoTimeRepeat(N, warmup)
    println("Str #: $strHash")

    val mapEquals = TimeIt {
        hashMap.equals(hashMap2)
    }.autoTimeRepeat(N, warmup)
    println("Map =: $mapEquals")

    val stringEquals = TimeIt {
        string.equals(string2)
    }.autoTimeRepeat(N, warmup)
    println("Str =: $stringEquals")
}
