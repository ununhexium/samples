package strings

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArrangementsTest {
  @Test
  fun `can increment`(){
    assertThat(Arrangements.increment("aaa")).isEqualTo("aab")
    assertThat(Arrangements.increment("aac")).isEqualTo("aba")
    assertThat(Arrangements.increment("acc")).isEqualTo("baa")
  }
}