import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Tests {
    @Test
    fun testExamples() {
        val input = listOf(1721L, 979, 366, 299, 675, 1456)
        val pair = pairThatSumsTo(input, 2020)
        val triple = tripleThatSumsTo2020(input)
        assertEquals(514579, pair.first * pair.second)
        assertEquals(241861950, triple.first * triple.second * triple.third)
    }

    @Test
    fun testInput() {
        val input = getLongs(1)
        val pair = pairThatSumsTo(input, 2020)
        val triple = tripleThatSumsTo2020(input)
        assertEquals(357504, pair.first * pair.second)
        assertEquals(12747392, triple.first * triple.second * triple.third)
    }

}
