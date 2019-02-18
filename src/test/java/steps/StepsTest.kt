package steps

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StepsTest {
  @Test
  fun `1 step`() {
    val res = Steps.computeWays(1)
    assertThat(res).isEqualTo(1)
  }

  @Test
  fun `1 step too small`() {
    val res = Steps.computeWays(1, listOf(2))
    assertThat(res).isEqualTo(0)
  }

  /*
   * 1 1
   * 2
   */
  @Test
  fun `2 steps`() {
    val res = Steps.computeWays(2)
    assertThat(res).isEqualTo(2)
  }

  @Test
  fun `2 steps 1 way`() {
    val res = Steps.computeWays(2, listOf(2))
    assertThat(res).isEqualTo(1)
  }

  /*
   * 1 1 1
   * 1 2
   * 2 1
   */
  @Test
  fun `3 steps`() {
    val res = Steps.computeWays(3)
    assertThat(res).isEqualTo(3)
  }

  /*
   * 1 1 1 1
   * 2 1 1
   * 1 2 1
   * 1 1 2
   * 2 2
   */
  @Test
  fun `4 steps`() {
    val res = Steps.computeWays(4)
    assertThat(res).isEqualTo(5)
  }

}