import kotlin.test.Test
import kotlin.test.assertEquals

fun speakNumber(starting: List<Int>, rank: Int): Int {
    var i = starting.size
    val n = starting.toMutableList()
    while (i < rank) {
        val head = n.dropLast(1)
        val tail = n.last()
        val r =
            if (!head.contains(tail)) 0
            else i - head.lastIndexOf(tail) - 1
        n.add(r)
        i++
    }
    return n.last()
}

fun speakNumberMap(starting: List<Int>, rank: Int): Int {
    val n = mutableMapOf<Int, Int>()
    starting.dropLast(1).withIndex().forEach { n[it.value] = it.index }
    var i = starting.size
    var l = starting.last()
    while (i < rank) {
        val r = if (!n.containsKey(l)) 0 else i - n[l]!! - 1
        n[l] = i - 1
        l = r
        i++
    }
    return l
}
class Day15Tests {

    @Test
    fun testExamples() {
        assertEquals(436, speakNumberMap(listOf(0, 3, 6), 2020))
        assertEquals(1, speakNumberMap(listOf(1, 3, 2), 2020))
        assertEquals(10, speakNumberMap(listOf(2, 1, 3), 2020))
        assertEquals(27, speakNumberMap(listOf(1, 2, 3), 2020))
        assertEquals(78, speakNumberMap(listOf(2, 3, 1), 2020))
        assertEquals(438, speakNumberMap(listOf(3, 2, 1), 2020))
        assertEquals(1836, speakNumberMap(listOf(3, 1, 2), 2020))
        assertEquals(175594, speakNumberMap(listOf(0, 3, 6), 30000000))
        assertEquals(2578, speakNumberMap(listOf(1, 3, 2), 30000000))
        assertEquals(3544142, speakNumberMap(listOf(2, 1, 3), 30000000))
        assertEquals(261214, speakNumberMap(listOf(1, 2, 3), 30000000))
        assertEquals(6895259, speakNumberMap(listOf(2, 3, 1), 30000000))
        assertEquals(18, speakNumberMap(listOf(3, 2, 1), 30000000))
        assertEquals(362, speakNumberMap(listOf(3, 1, 2), 30000000))
    }

    @Test
    fun testInput() {
        val input = getInput(15).split(",").map { it.toInt() }
        assertEquals(260, speakNumberMap(input, 2020))
        assertEquals(950, speakNumberMap(input, 30000000))
    }
}
