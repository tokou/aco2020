import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Tests {

    private val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)

    @Test
    fun testExamples() {
        val input = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent()
        val forest = Forest.parse(input)
        assertEquals(7, forest.downSlope().size)
        assertEquals(336, slopes.fold(1) { acc, s -> acc * forest.downSlope(s).size })
    }

    @Test
    fun testInput() {
        val forest = Forest.parse(getInput(3))
        assertEquals(242, forest.downSlope().size)
        assertEquals(2265549792, slopes.fold(1L) { acc, s -> acc * forest.downSlope(s).size })
    }
}
