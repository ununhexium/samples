package stream.vs.kotlin.func

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.stream.Collectors

/**
 * To run with Xmx 128m, to show the memory overhead
 */
internal class JavaStreamVsKotlinFunctionalKtTest
{
  companion object
  {
    val MAX = 128
  }

  @Test
  fun `allocate 128M of RAM fails`()
  {
    assertThrows<OutOfMemoryError> {
      val array = (0..MAX).map {
        Array(1024 * 1024 / 8, { it.toLong() })
      }
      println(array.last())
    }
  }

  @Test
  fun `Java processes collection items 1 by 1`()
  {
    val result = (0..MAX).toList().stream().map {
      // allocate a lot of memory
      Array(1024 * 1024 / 8, { it.toLong() })
    }.filter {
      true
    }.map {
      // free memory
      it.last()
    }.collect(Collectors.toList())

    println(result.size)
  }

  @Test
  fun `Kotlin processes collections step by step`()
  {
    assertThrows<OutOfMemoryError> {
      val result = (0..MAX).toList().map {
        // allocate a lot of memory
        Array(1024 * 1024 / 8, { it.toLong() })
      }.filter {
        true
      }.map {
        it.last()
      }

      println(result)
    }
  }

  @Test
  fun `Kotlin processes sequences items 1 by 1`()
  {
    val result = (0..MAX *2).toList().asSequence().map {
      // allocate a lot of memory
      Array(1024 * 1024 / 8, { it.toLong() })
    }.filter {
      true
    }.map {
      it.last()
    }
    println(result.toList().last())
  }
}
