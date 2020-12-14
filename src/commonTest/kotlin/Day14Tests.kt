import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals


fun MutableMap<Long, Long>.initialize(input: List<String>): MutableMap<Long, Long> {
    val pattern = input.first().split(" = ").last()
    val instructions = input.drop(1).map { l ->
        val (addr, value) = l.drop(4).split("] = ").map { it.toLong() }
        addr to value
    }
    val andMask = pattern.replace('1', '0').replace('X', '1').toLong(2)
    val orMask = pattern.replace('X', '0').toLong(2)
    instructions.forEach { set(it.first, (it.second and andMask) or orMask) }
    return this
}

fun <T> List<T>.combinations(): List<List<T>> {
    val result = mutableListOf<MutableList<T>>().apply {
        add(mutableListOf())
        last().add(this@combinations[0])
    }

    if (size == 1) return result

    drop(1).combinations().forEach { combo ->
        result.add(combo.toMutableList())
        result.add((combo + subList(0, 1)).toMutableList())
    }

    return result
}

fun MutableMap<Long, Long>.initialize2(input: List<String>): MutableMap<Long, Long> {
    val pattern = input.first().split(" = ").last()
    val instructions = input.drop(1).map { l ->
        val (addr, value) = l.drop(4).split("] = ").map { it.toLong() }
        addr to value
    }
    val orMask = pattern.replace('X', '0').toLong(2)
    val xMask = pattern.replace('0', '1').replace('X', '0').toLong(2)
    val xPattern = pattern.replace('1', '0').replace('0', '-')
    val xIndices = xPattern.withIndex().filter { it.value == 'X' }.map { it.index }
    val xPowers = xIndices.map { 2.0.pow(36 - it - 1).toLong() }

    val combinations = xPowers.combinations() + listOf(listOf(0L))
    instructions.forEach { (key, value) ->
        val masked = (key or orMask) and xMask
        combinations.forEach { c ->
            val address = masked + c.sum()
            set(address, value)
        }
    }
    return this
}

class Day14Tests {

    @Test
    fun testExamples() {
        val input = """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0
        """.trimIndent().lines()
        assertEquals(165, mutableMapOf<Long, Long>().initialize(input).values.sum())

        val inputFloating = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().lines().chunked(2)
        val mem = mutableMapOf<Long, Long>()
        inputFloating.forEach {
            mem.initialize2(it)
        }
        assertEquals(208, mem.values.sum())
    }

    @Test
    fun testInput() {
        val input = getInput(14)
        val instructions = input.lines().fold(mutableListOf<MutableList<String>>()) { res, l ->
            if (l.startsWith("mask")) res.add(mutableListOf(l))
            else res.last().add(l)
            res
        }
        val mem = mutableMapOf<Long, Long>()
        val memFloating = mutableMapOf<Long, Long>()
        instructions.forEach {
            mem.initialize(it)
            memFloating.initialize2(it)
        }
        assertEquals(11179633149677, mem.values.sum())
        assertEquals(4822600194774, memFloating.values.sum())
    }
}
