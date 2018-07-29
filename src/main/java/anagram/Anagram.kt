package anagram

import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.Instant
import kotlin.system.measureTimeMillis

/**
 * Finds and lists english anagrams by number of letters
 */
fun main(args: Array<String>) {

    checkpoint()
    val path = "/home/ununhexium/dev/data/english/words.txt"
    val words = Files.readAllLines(Paths.get(path))

    val benchmark = listOf(
        ::naive,
        ::better
    ).map { method ->
        method to measureTimeMillis {
            findAnagrams(words, method)
        }
    }

    benchmark.forEach {
        println(it.first.name + " " + it.second)
    }
}

private fun findAnagrams(
    words: MutableList<String>,
    keyBuilder: (String) -> String
) {
    checkpoint()
    println("READ")
    val anagrams = words
        .groupBy {
            keyBuilder(it)
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
    checkpoint()
    println("Found ${anagrams.size} anagrams")
}

fun better(string: String): String {
    val inplace = string.toLowerCase().toCharArray().sort()
    return inplace.toString()
}

private fun naive(it: String) =
    it.toLowerCase().toList().sorted().joinToString(separator = "")

var time = Instant.now()

fun checkpoint() {
    val now = Instant.now()
    println(Duration.between(time, now))
    time = now
}
