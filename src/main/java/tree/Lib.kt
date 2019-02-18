package tree

import kotlin.math.max

class MyNode(val left: MyNode?, val right: MyNode?) {

}

object Lib {
  /**
   * @param node The node to explorer from and count how many children it has.
   *
   * @return The number of nodes, itself included,
   * or 0 if the given parameter is null.
   */
  fun countNodes(node: MyNode?): Long {
    return if (node == null) 0
    else 1 + countNodes(node.left) + countNodes(node.right)
  }

  fun maxDepth(node: MyNode): Long {
    return internalMaxDepth(node) - 1
  }

  private fun internalMaxDepth(node: MyNode?): Long {
    return if (node == null) 0
    else max(
        internalMaxDepth(node.left) + 1,
        internalMaxDepth(node.right) + 1
    )
  }

  fun deepest(node: MyNode, startDepth: Int = 0): Pair<MyNode, Int> {
    return if (node.left == null && node.right == null) {
      node to startDepth
    }
    else if (node.left == null) {
      return deepest(node.right!!, startDepth + 1)
    }
    else if (node.right == null) {
      return deepest(node.left)
    }
    else {
      return listOf(deepest(node.left), deepest(node.right)).maxBy {
        it.second
      }!!
    }
  }
}
