import kotlin.test.Test
import kotlin.test.assertEquals


fun List<Int>.differenceInJolts(): Pair<Int, Int> {
    val s = listOf(0) + sorted() + listOf(maxOrNull()!! + 3)
    val diffs = s.zipWithNext().map { it.second - it.first }
    return diffs.count { it == 1 } to diffs.count { it == 3 }
}

fun List<Int>.countArrangements(): Long {
    val s = listOf(0) + sorted() + listOf(maxOrNull()!! + 3)
    val diffs = s.zipWithNext().map { it.second - it.first }
    val i = diffs.joinToString("")
        .split("3")
        .filter { it.isNotEmpty() }
        .filterNot { it == "1" }
        .map { countPermutations(it.length) }
    return i.fold(1L) { acc, e -> acc * e }
}

// [2]
// 1, 1
// 2
// [3]
// 1, 1, 1
// 2, 1
// 1, 2
// 3
// [4]
// 1, 1, 1, 1
// 2, 1, 1
// 1, 2, 1
// 1, 1, 2
// 2, 2
// 3, 1
// 1, 3
// [5]
// 1, 1, 1, 1, 1
// 2, 1, 1, 1
// 1, 2, 1, 1
// 1, 1, 2, 1
// 1, 1, 1, 2
// 3, 1, 1
// 1, 3, 1
// 1, 1, 3
// 2, 2, 1
// 2, 1, 2
// 1, 2, 2
// 3, 2
// 2, 3
// [6]
// 1, 1, 1, 1, 1, 1
// 2, 1, 1, 1, 1
// 1, 2, 1, 1, 1
// 1, 1, 2, 1, 1
// 1, 1, 1, 2, 1
// 1, 1, 1, 1, 2
// 3, 1, 1, 1
// 1, 3, 1, 1
// 1, 1, 3, 1
// 1, 1, 1, 3
// 2, 2, 1, 1
// 2, 1, 2, 1
// 2, 1, 1, 2
// 1, 2, 2, 1
// 1, 2, 1, 2
// 1, 1, 2, 2
// 3, 2, 1
// 3, 1, 2
// 1, 3, 2
// 2, 3, 1
// 1, 2, 3
// 2, 1, 3
// 3, 3
// This looks like Pascal's triangle
// Find a generalized formula someday
private fun countPermutations(length: Int) = when (length) {
    2 -> 1 + 1
    3 -> 1 + 2 + 1
    4 -> 1 + 3 + 3
    5 -> 1 + 4 + 6 + 2
    6 -> 1 + 5 + 10 + 6 + 1
    else -> error("cannot compute length of $length")
}

class Day10Tests {
    @Test
    fun testExamples() {
        val input = """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
        """.trimIndent()
        val input2 = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent()
        val jolts = input.split("\n").map { it.toInt() }
        val jolts2 = input2.split("\n").map { it.toInt() }
        val joltsDiff = jolts.differenceInJolts()
        val joltsDiff2 = jolts2.differenceInJolts()
        assertEquals(7 * 5, joltsDiff.first * joltsDiff.second)
        assertEquals(22 * 10, joltsDiff2.first * joltsDiff2.second)
        assertEquals(8, jolts.countArrangements())
        assertEquals(19208, jolts2.countArrangements())
    }

    @Test
    fun testInput() {
        val jolts = getInts(10)
        val joltsDiff = jolts.differenceInJolts()
        assertEquals(1856, joltsDiff.first * joltsDiff.second)
        assertEquals(2314037239808, jolts.countArrangements())
    }
}
