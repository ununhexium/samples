//package fizz.buzz
//
//import org.hibernate.validator.internal.util.Contracts.assertTrue
//import java.util.Random
//import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt
//
//// Write a method to shuffle a deck of cards.
//// It must be a perfect shuffle - in other words, each n! permutations of the deck of n cards has to be equally likely.
//// Assume that you are given a random number generator which is perfect.
//// Test this method.
//
//
//val random = Random()
//
//fun main(args:List<String>) {
//  val cards = (0..32).toList().toTypedArray()
//  shuffles(cards)
//}
//
//public fun shuffle(card:Array<Int>) {
//  schuffle(cards, random)
//}
//
//private fun shuffle(cards:Array<Int>, random: Random) {
//  val copy = mutableListOf(*cards)
//  val schuffled = mutableListOf<Int>()
//  while(copy.isNotEmpty()) {
//    val index = random.nextInt(copy.size)
//    schuffled.add(copy.removeAt(index))
//  }
//  (0 until schuffled.size).forEach{ index ->
//    cards[index] = schuffled[index]
//  }
//}
//
//
///**
//int [] more = {0, 1, 2}
// */
//val more = IntArray(3) { index -> index}
//
//class SchuffleKtTest {
//  @Test
//  fun `can shuffle 0 cards`() {
//    // given
//    val src = IntArray(0){index -> 0} // int[] tmp = new int[]
//
//    // on
//    schuffle(src)
//
//    // then
//    assertTrue(src.equals(IntArray(0){0}))
//  }
//
//  @Test
//  fun `can shuffle 10 cards`(){
//    // given
//    val r = Random(10)
//    val src = IntArray(10){index -> index} // int[] tmp = new int[]
//
//
//    // on
//    schuffle(src)
//
//    // then
//    assertFalse(
//        src.equals(IntArray(10){index -> index})
//    )
//  }
//}