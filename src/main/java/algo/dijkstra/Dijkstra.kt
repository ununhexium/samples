package algo.dijkstra

import com.google.common.graph.ValueGraph
import java.util.LinkedList
import java.util.Optional

object Dijkstra {
    val INF = Double.POSITIVE_INFINITY

    var print = false

    fun distance(g: ValueGraph<String, Double>, a: String, b: String): Double {
        val nodes = g.nodes().filter {
            it != b
        }.mapTo(LinkedList()) {
            it
        }
        println("Preparing to browse " + nodes.joinToString())

        val distances = LinkedHashMap<String, Pair<String, Double>>()
        distances[a] = a to 0.0

        var current: String = a
        while (distances[b] == null) {
            println("Currently at $current")
            g.adjacentNodes(current).forEach {
                val distance = g.edgeValue(current, it).orIAE()
                if (distance < distances[it]?.second ?: INF) {
                    distances[it] = current to (distance + distances[current]!!.second)
                }
            }

            println("Best distance: " + distances[b])
            nodes.remove(current)
            current = distances.filter {
                it.key in nodes
            }.minBy {
                it.value.second
            }?.key ?: continue
        }

        return distances[b]?.second ?: INF
    }

    private fun <T> Optional<T>.orIAE() = this.orElseThrow {
        IllegalArgumentException("All edges must be weighted")
    }
}
