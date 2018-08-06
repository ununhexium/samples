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
      ::better,
      ::groupBy
  ).map { method ->
    lateinit var result: Map<String, List<String>>
    val time = measureTimeMillis {
      result = findAnagrams(words, method)
    }
    Triple(method, time, result)
  }

  benchmark.forEachIndexed { index, _ ->
    if (benchmark[0].third != benchmark[index].third) {
      throw IllegalStateException("Inconsistent result")
    }
  }

  benchmark.forEach {
    println(it.first.name + " " + it.second)
  }
}

private fun findAnagrams(
    words: MutableList<String>,
    keyBuilder: (String) -> String
): Map<String, List<String>> {
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

  return anagrams
}

fun better(string: String): String {
  val inplace = string.toLowerCase().toCharArray()
  inplace.sort()
  return String(inplace)
}

fun naive(it: String) =
    it.toLowerCase().toList().sorted().joinToString(separator = "")

fun groupBy(it: String): String =
    it.toLowerCase().groupingBy {
      it
    }.eachCount().entries.sortedBy {
      it.key
    }.joinToString("") {
      it.key.toString().repeat(it.value)
    }

var time = Instant.now()

fun checkpoint() {
  val now = Instant.now()
  println(Duration.between(time, now))
  time = now
}
