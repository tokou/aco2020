import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Tests {
    @Test
    fun testExamples() {
        val input = """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576
        """.trimIndent()
        val longs = input.split("\n").map { it.toLong() }
        assertEquals(127, sumsInPreamble(longs, 5).firstFalse())
        assertEquals(62, sumsContiguousTo(longs, 127).sumMinMax())
    }

    @Test
    fun testInput() {
        val longs = getLongs(9)
        val firstFalse = sumsInPreamble(longs).firstFalse()
        assertEquals(32321523, firstFalse)
        val contiguousSum = sumsContiguousTo(longs, firstFalse)
        assertEquals(4794981, contiguousSum.sumMinMax())
    }
}
