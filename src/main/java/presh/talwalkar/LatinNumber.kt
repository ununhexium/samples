package presh.talwalkar

fun Int.has1DigitOfEach() =
    this.toString().groupBy { it }.values.map {
        it.size
    }.groupBy { it }[1]?.size == 6


fun List<Int>.has1DigitOfEachByColumn() =
    (0..5).all { index ->
        this.map {
            it.toString()[index]
        }.groupBy {
            it
        }.values.map {
            it.size
        }.groupBy {
            it
        }[1]?.size == 6
    }

/*
 * https://www.youtube.com/watch?v=KXOjtmNUSH0
 */
fun main(args: Array<String>) {
    val toFind = (123456..987654)
        .filter { it * 6 < 999999 }
        .map { candidate ->
            (1..6).map { candidate * it }.filter {
                it.has1DigitOfEach()
            }
        }
        .filter {
            it.has1DigitOfEachByColumn()
        }
        .filter { it.size == 6 }

    println(toFind)
}
