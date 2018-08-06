package algo.dijkstra

import com.google.common.graph.ValueGraphBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DijkstraTest {
    @Test
    fun `Can link 2 neighbors`() {
        val g = ValueGraphBuilder.undirected().build<String, Double>()
        g.addNode("A")
        g.addNode("Z")
        g.putEdgeValue("A", "Z", 1.0)

        assertThat(Dijkstra.distance(g, "A", "Z")).isEqualTo(1.0)
    }

    @Test
    fun `Can link over a middle node neighbors`() {
        val g = ValueGraphBuilder.undirected().build<String, Double>()
        g.addNode("A")
        g.addNode("B")
        g.addNode("Z")
        g.putEdgeValue("A", "B", 1.0)
        g.putEdgeValue("B", "Z", 2.0)

        assertThat(Dijkstra.distance(g, "A", "Z")).isEqualTo(3.0)
    }

    @Test
    fun `Can find a better path using more nodes`() {
        val g = ValueGraphBuilder.undirected().build<String, Double>()
        g.addNode("A")
        g.addNode("B")
        g.addNode("D")
        g.addNode("E")
        g.addNode("Z")
        g.putEdgeValue("A", "B", 10.0)
        g.putEdgeValue("B", "Z", 10.0)
        g.putEdgeValue("A", "D", 1.0)
        g.putEdgeValue("D", "E", 1.0)
        g.putEdgeValue("E", "Z", 1.0)

        assertThat(Dijkstra.distance(g, "A", "Z")).isEqualTo(3.0)
    }

    /**
     * https://www.youtube.com/watch?v=GazC3A4OQTE
     */
    @Test
    fun `Computerphile`() {
        val g = ValueGraphBuilder.undirected().build<String, Double>()
        ('A'..'L').forEach {
            g.addNode(it.toString())
        }
        g.addNode("S")
        Dijkstra.print = true

        g.putEdgeValue("S", "A", 7.0)
        g.putEdgeValue("S", "B", 2.0)
        g.putEdgeValue("S", "C", 3.0)
        g.putEdgeValue("A", "B", 3.0)
        g.putEdgeValue("B", "D", 4.0)
        g.putEdgeValue("A", "D", 4.0)
        g.putEdgeValue("B", "H", 1.0)
        g.putEdgeValue("H", "F", 3.0)
        g.putEdgeValue("D", "F", 5.0)
        g.putEdgeValue("H", "G", 2.0)
        g.putEdgeValue("G", "E", 2.0)
        g.putEdgeValue("C", "L", 2.0)
        g.putEdgeValue("L", "I", 4.0)
        g.putEdgeValue("L", "J", 4.0)
        g.putEdgeValue("I", "J", 4.0)
        g.putEdgeValue("I", "K", 4.0)
        g.putEdgeValue("J", "K", 4.0)
        g.putEdgeValue("K", "E", 5.0)

        assertThat(Dijkstra.distance(g, "S", "E")).isEqualTo(7.0)
    }
}
