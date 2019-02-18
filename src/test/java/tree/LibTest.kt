package tree

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LibTest {
  @Test
  fun `node depth of leaf is 0`() {
    assertThat(Lib.maxDepth(MyNode(null, null))).isEqualTo(0)
  }

  @Test
  fun `deepest node is 2`() {
    assertThat(
        Lib.maxDepth(
            MyNode(
                MyNode(
                    MyNode(null, null),
                    MyNode(null, null)
                ),
                null
            )
        )
    ).isEqualTo(0)
  }

}