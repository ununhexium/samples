package fizz.buzz

import org.funktionale.currying.curried

/**
 * Originally from https://youtu.be/dC9vdQkU-xI?t=25m33s
 */

val tester: (Int, Int, String, (String) -> String) -> (String) -> String =
    { value, divisor, name, nextTest ->
        if (value % divisor == 0) ({ name + nextTest("") }) else nextTest
    }

fun fizzBuzz(n: Int): String
{
    val fizzer = tester.curried()(n)(3)("Fizz")
    val buzzer = tester.curried()(n)(5)("Buzz")
    return fizzer(buzzer { it })(n.toString())
}

/**
 * Tests for the special cases and append the replacement value
 * in the order they appear in the list.
 */
fun genericFizzBuzz(map: List<Pair<Int, String>>, n: Int): String
{
    val testers = map.map {
        tester.curried()(n)(it.first)(it.second)
    }

    val generic = testers.foldRight({ it }, { t, r: (String) -> String -> t(r) })
    return generic(n.toString())

}
