import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals


enum class Direction {
    North, East, South, West
}

typealias Ship = Triple<Direction, Int, Int>

fun Ship.move(instruction: String): Ship {
    println("ship $this moving $instruction")
    val command = instruction.first()
    val parameter = instruction.drop(1).toInt()
    return when (command) {
        'N' -> Triple(first, second, third + parameter)
        'E' -> Triple(first, second + parameter, third)
        'S' -> Triple(first, second, third - parameter)
        'W' -> Triple(first, second - parameter, third)
        'F' -> when (first) {
            Direction.North -> Triple(first, second, third + parameter)
            Direction.East -> Triple(first, second + parameter, third)
            Direction.South -> Triple(first, second, third - parameter)
            Direction.West -> Triple(first, second - parameter, third)
        }
        'R' -> {
            val d = Direction.values()
            val i = d.indexOf(first)
            val n = d[(i + parameter / 90) % d.size]
            Triple(n, second, third)
        }
        'L' -> {
            val d = Direction.values()
            val i = d.indexOf(first)
            val n = d[(i - parameter / 90 + d.size) % d.size]
            Triple(n, second, third)
        }
        else -> this
    }
}

typealias Ship2 = Pair<Pair<Int, Int>, Pair<Int, Int>>

operator fun Int.times(other: Pair<Int, Int>): Pair<Int, Int> = other.first * this to other.second * this
operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

fun Ship2.move(instruction: String): Ship2 {
    println("ship2 $this moving $instruction")
    val command = instruction.first()
    val parameter = instruction.drop(1).toInt()
    val (waypoint, position) = this
    return when (command) {
        'N' -> (waypoint.first to waypoint.second + parameter) to position
        'E' -> (waypoint.first + parameter to waypoint.second) to position
        'S' -> (waypoint.first to waypoint.second - parameter) to position
        'W' -> (waypoint.first - parameter to waypoint.second) to position
        'F' -> waypoint to (position + parameter * waypoint)
        'R' -> when (parameter) {
            90 -> (waypoint.second to -waypoint.first) to position
            180 -> (-1 * waypoint) to position
            270 -> (-waypoint.second to waypoint.first) to position
            else -> this
        }
        'L' -> when (parameter) {
            90 -> (-waypoint.second to waypoint.first) to position
            180 -> (-1 * waypoint) to position
            270 -> (waypoint.second to -waypoint.first) to position
            else -> this
        }
        else -> this
    }
}

class Day12Tests {
    @Test
    fun testExamples() {
        val input = """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent()
        val ship: Ship = Triple(Direction.East, 0, 0)
        val r = input.lines().fold(ship) { s, i -> s.move(i) }
        assertEquals(25, r.second.absoluteValue + r.third.absoluteValue)
        val ship2: Ship2 = (10 to 1) to (0 to 0)
        val rr = input.lines().fold(ship2) { s, i -> s.move(i) }.second
        assertEquals(286, rr.first.absoluteValue + rr.second.absoluteValue)
    }

    @Test
    fun testInput() {
        val input = getLines(12)
        val ship: Ship = Triple(Direction.East, 0, 0)
        val r = input.fold(ship) { s, i -> s.move(i) }
        assertEquals(1457, r.second.absoluteValue + r.third.absoluteValue)
        val ship2: Ship2 = (10 to 1) to (0 to 0)
        val rr = input.fold(ship2) { s, i -> s.move(i) }.second
        assertEquals(106860, rr.first.absoluteValue + rr.second.absoluteValue)
    }
}
