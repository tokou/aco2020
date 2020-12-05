import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Tests {
    @Test
    fun testExamples() {
        val input = listOf(1721, 979, 366, 299, 675, 1456)
        val pair = pairThatSumsTo2020(input)
        val triple = tripleThatSumsTo2020(input)
        assertEquals(514579, pair.first * pair.second)
        assertEquals(241861950, triple.first * triple.second * triple.third)
    }

    @Test
    fun testInput() {
        val input = getInts(1)
        val pair = pairThatSumsTo2020(input)
        val triple = tripleThatSumsTo2020(input)
        assertEquals(357504, pair.first * pair.second)
        assertEquals(12747392, triple.first * triple.second * triple.third)
    }

}
