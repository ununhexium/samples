package anagram

import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.Instant

/**
 * Finds and lists english anagrams by number of letters
 */
fun main(args: Array<String>) {

    checkpoint()
    val path = "/home/ununhexium/dev/data/english/words.txt"
    val words = Files.readAllLines(Paths.get(path))

    checkpoint()
    println("READ")
    words
        .groupBy {
            it.map { it }.sorted().joinToString(separator = "")
        }
        .also {
            checkpoint()
            println("Grouped")
        }
        .filter {
            it.value.size > 1
        }
        .also {
            checkpoint()
            println("Filtered")
        }
        .forEach {
            println(it.value.joinToString(separator = ", ") { it })
        }
    checkpoint()
}

var time = Instant.now()

fun checkpoint() {
    val now = Instant.now()
    println(Duration.between(time, now))
    time = now
}
