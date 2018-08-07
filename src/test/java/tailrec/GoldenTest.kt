package tailrec

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class GoldenTest {
  @Test
  fun `can compute ratio`() {
    assertThat(Golden.compute()).isEqualTo(
        (1 + sqrt(5.0)) / 2.0,
        Offset.offset(Double.MIN_VALUE)
    )
  }
}