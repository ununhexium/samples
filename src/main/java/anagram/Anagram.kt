package anagram

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Finds and lists english anagrams by number of letters
 */
fun main(args: Array<String>) {

    val path = "/home/ununhexium/dev/data/english/words.txt"
    val words = Files.readAllLines(Paths.get(path))
    println("READ")
    words
        .map {word ->
            word to word.groupBy { it }.mapValues { it.value.size }
        }
        .also {
            println("Mapped")
        }
        .groupBy {
            it.second
        }
        .also {
            println("Grouped")
        }
        .mapValues {
            it.value.map { it.first }
        }
        .also {
            println("Mapped 2")
        }
        .filter {
            it.value.size > 1
        }
        .also {
            println("Filtered")
        }
        .forEach {
            println(it.value.joinToString(separator = ", ") { it })
        }
}

