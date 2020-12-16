import kotlin.test.Test
import kotlin.test.assertEquals

fun getInvalidFields(input: String): List<Int> {
    val (rulesInput, mineInput, nearbyInput) = input.split("\n\n")
    val mine = mineInput.lines().last().split(",").map { it.toInt() }
    val nearby = nearbyInput.lines().drop(1).map { l -> l.split(",").map { it.toInt() } }
    val rules = rulesInput.lines().map { i ->
        val (rule, rangeInput) = i.split(": ")
        val ranges = rangeInput.split(" or ").map { r ->
            val (f, t) = r.split("-").map { it.toInt() }
            (f..t)
        }
        rule to ranges
    }.toMap()
    return nearby.flatten().filter { v -> rules.values.flatten().none { v in it } }
}

fun getMineFields(input: String): Map<String, Int> {
    val (rulesInput, mineInput, nearbyInput) = input.split("\n\n")
    val mine = mineInput.lines().last().split(",").map { it.toInt() }
    val nearby = nearbyInput.lines().drop(1).map { l -> l.split(",").map { it.toInt() } }
    val rules = rulesInput.lines().map { i ->
        val (rule, rangeInput) = i.split(": ")
        val ranges = rangeInput.split(" or ").map { r ->
            val (f, t) = r.split("-").map { it.toInt() }
            (f..t)
        }
        rule to ranges
    }
    val valid = nearby.filter { t -> t.all { v -> rules.flatMap { it.second }.any { v in it } } }
    val fields = valid.first().mapIndexed { i, _ -> valid.map { it[i] } }
    var candidates = fields.map { f -> rules.filter { r -> f.all { n -> r.second.any { n in it } } }.map { it.first } }
    while (candidates.flatten().size > candidates.size) {
        candidates = candidates.map { l ->
            val singles = candidates.filter { it.size == 1 }.map { it.first() }
            if (l.size > 1) l.filterNot { it in singles }
            else l
        }
    }
    val keys = candidates.flatten()
    return keys.zip(mine).toMap()
}


class Day16Tests {

    @Test
    fun testExamples() {
        val input = """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50

            your ticket:
            7,1,14

            nearby tickets:
            7,3,47
            40,4,50
            55,2,20
            38,6,12
        """.trimIndent()
        assertEquals(71, getInvalidFields(input).sum())

        val input2 = """
            class: 0-1 or 4-19
            row: 0-5 or 8-19
            seat: 0-13 or 16-19

            your ticket:
            11,12,13

            nearby tickets:
            3,9,18
            15,1,5
            5,14,9
        """.trimIndent()
        assertEquals(mapOf("class" to 12, "row" to 11, "seat" to 13), getMineFields(input2))
    }

    @Test
    fun testInput() {
        val input = getInput(16)
        assertEquals(20058, getInvalidFields(input).sum())
        val departureValues = getMineFields(input).filterKeys { it.startsWith("departure") }.values
        assertEquals(366871907221, departureValues.fold(1L) { acc, v -> acc * v.toLong()})
    }
}
