package fizz.buzz

import org.assertj.core.api.Assertions.assertThat
import org.funktionale.currying.curried
import org.junit.jupiter.api.Test

internal class FizzBuzzKtTest
{
    private fun fizzBuzzTest(fizzBuzz: (Int) -> String)
    {
        assertThat(fizzBuzz(0)).isEqualTo("FizzBuzz")
        assertThat(fizzBuzz(1)).isEqualTo("1")
        assertThat(fizzBuzz(3)).isEqualTo("Fizz")
        assertThat(fizzBuzz(5)).isEqualTo("Buzz")
        assertThat(fizzBuzz(15)).isEqualTo("FizzBuzz")
    }

    @Test
    fun `basic fizz buzz works`()
    {
        val basic = ::fizzBuzz
        fizzBuzzTest(basic)
    }

    @Test
    fun `generic fizz buzz works for basic case`()
    {
        val params = listOf(
            3 to "Fizz",
            5 to "Buzz"
        )

        val generic = ::genericFizzBuzz.curried()(params)
        fizzBuzzTest(generic)
    }

    @Test
    fun `generic fizz buzz is extendable to more complicated cases`()
    {
        val params = listOf(
            3 to "Fizz",
            5 to "Buzz",
            7 to "Foo"
        )

        val generic = ::genericFizzBuzz.curried()(params)
        assertThat(generic(0)).isEqualTo("FizzBuzzFoo")
        assertThat(generic(3 * 5 * 7)).isEqualTo("FizzBuzzFoo")
        assertThat(generic(7)).isEqualTo("Foo")
    }

    @Test
    fun `the order of parameters is important`()
    {
        val params = listOf(
            5 to "Buzz",
            3 to "Fizz"
        )

        val generic = ::genericFizzBuzz.curried()(params)
        assertThat(generic(0)).isEqualTo("BuzzFizz")
    }
}
