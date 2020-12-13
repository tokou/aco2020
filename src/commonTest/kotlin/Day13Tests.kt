import kotlin.test.Test
import kotlin.test.assertEquals

fun earliestBus(time: String, lines: String): Pair<Int, Int> {
    val time = time.toInt()
    val lines = lines.split(",").filterNot { it == "x" }.map { it.toInt() }
    val delta = lines.map { it - time % it }
    val index = delta.withIndex().minByOrNull { it.value }?.index ?: -1
    return delta[index] to lines[index]
}

fun earliestAllBusesBruteforce(lines: String): Long {
    val ids = lines
        .split(",")
        .withIndex()
        .filterNot { it.value == "x" }
        .map { it.value.toLong() to it.index.toLong() }
    println(ids)
    var t = 0L
    while (ids.any { (t + it.second) % it.first != 0L }) { t += ids[0].first }
    return t
}

fun Long.floorMod(mod: Long): Long = ((this % mod) + mod) % mod

// https://fr.wikipedia.org/wiki/Algorithme_d%27Euclide_étendu#Pseudo-code
fun eucl(a: Long, b: Long): Pair<Long, Long> {
    if (b == 0L) return 1L to 0L
    val (u, v) = eucl(b, a % b)
    return v to (u - (a / b) * v)
}

// https://fr.wikipedia.org/wiki/Théorème_des_restes_chinois#Algorithme
fun earliestAllBusesArithmetic(lines: String): Long {
    val ids = lines
        .split(",")
        .withIndex()
        .filterNot { it.value == "x" }
        .map { it.value.toLong() to it.index.toLong() }
    val diffs = ids.map { (-it.second).floorMod(it.first) to it.first }
    val a = diffs.map { it.first }
    val nis = diffs.map { it.second }
    val n = nis.fold(1L) { acc, ni -> acc * ni }
    val nn = nis.map { ni -> n / ni }
    val eis = nis.zip(nn).map { (ni, nni) -> eucl(ni, nni).second.floorMod(ni) * nni }
    val x = eis.zip(a).map { it.first * it.second }.sum()
    return x % n
}


class Day13Tests {
    @Test
    fun testExamples() {
        val (timeInput, linesInput) = """
            939
            7,13,x,x,59,x,31,19
        """.trimIndent().lines()
        val earliestBus = earliestBus(timeInput, linesInput)
        assertEquals(295, earliestBus.first * earliestBus.second)

        assertEquals(1068781, earliestAllBusesBruteforce(linesInput))
        assertEquals(1068781, earliestAllBusesArithmetic(linesInput))

        val inputs = listOf(
            "17,x,13,19" to 3417L,
            "67,7,59,61" to 754018L,
            "67,x,7,59,61" to 779210L,
            "67,7,x,59,61" to 1261476L,
            "1789,37,47,1889" to 1202161486L
        )

        inputs.forEach {
            assertEquals(it.second, earliestAllBusesBruteforce(it.first))
            assertEquals(it.second, earliestAllBusesArithmetic(it.first))
        }
    }

    @Test
    fun testInput() {
        val (timeInput, linesInput) = getLines(13)
        val earliestBus = earliestBus(timeInput, linesInput)
        assertEquals(174, earliestBus.first * earliestBus.second)
        assertEquals(780601154795940, earliestAllBusesArithmetic(linesInput))
    }
}
