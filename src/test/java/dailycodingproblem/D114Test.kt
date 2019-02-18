package dailycodingproblem

import dailycodingproblem.D114.reverseWordsOfASentence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class D114Test {
    @Test
    fun `can reverse hello world`() {
        val i = "Hello world!"
        assertThat(reverseWordsOfASentence(i)).isEqualTo("world! Hello")
    }

    @Test
    fun `can reverse words`() {
        val i = "qwe asd zxc"
        assertThat(reverseWordsOfASentence(i)).isEqualTo("zxc asd qwe")
    }
}
