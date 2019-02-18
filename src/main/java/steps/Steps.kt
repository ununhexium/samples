package steps

object Steps {
  class Node(
      var stepSize: Int,
      val children: MutableList<Node> = mutableListOf()
  )

  fun computeWays(n: Int, steps: List<Int> = listOf(1, 2)): Long {
    // can finish?
    val min = steps.min()!!
    if (n == 0) {
      return 1
    }
    else {
      // can I progress?
      if (n < min) {
        return 0
      }
      // where can I go?
      val alternatives = steps.filter { it <= n }
      val alternativeWays = alternatives.map { computeWays(n - it, steps) }
      return alternativeWays.sum()
    }
  }
}