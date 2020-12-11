import kotlin.test.Test
import kotlin.test.assertEquals

enum class Tile {
    Empty, Floor, Occupied
}

typealias Seats = List<List<Tile>>

fun Seats.print(): String = joinToString("\n") { l ->
    l.joinToString("") { t -> when (t) {
        Tile.Empty -> "L"
        Tile.Floor -> "."
        Tile.Occupied -> "#"
    } }
}

fun String.parseSeats(): Seats = lines().map { l -> l.map { c -> when (c) {
    'L' -> Tile.Empty
    '#' -> Tile.Occupied
    else -> Tile.Floor
} } }

fun Pair<Int, Int>.circle(n: Int = 1) = listOf(
    first to second + n,
    first to second - n,
    first + n to second,
    first - n to second,
    first + n to second + n,
    first - n to second - n,
    first + n to second - n,
    first - n to second + n,
)

fun Pair<Int, Int>.neighbours(i: Int, j: Int) = circle()
    .filter { it.first in 0 until i && it.second in 0 until j }
    .toSet()

fun Pair<Int, Int>.line(dx: Int, dy: Int, l: Int) = (1..l).map { first + dx * it to second + dy * it }

val nnn = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, List<List<Pair<Int, Int>>>>()

fun Pair<Int, Int>.neighbourLines(i: Int, j: Int): List<List<Pair<Int, Int>>> {

    val k = this to (i to j)

    if (nnn.containsKey(k)) return nnn[k]!!

    val n = listOf(
        line(0, 1, maxOf(i, j)),
        line(0, -1, maxOf(i, j)),
        line(1, 0, maxOf(i, j)),
        line(-1, 0, maxOf(i, j)),
        line(1, 1, maxOf(i, j)),
        line(-1, -1, maxOf(i, j)),
        line(1, -1, maxOf(i, j)),
        line(-1, 1, maxOf(i, j)),
    ).map { l ->
        l.filter { it.first in 0 until i && it.second in 0 until j }
    }

    nnn[k] = n

    return n
}

fun Seats.get(coords: Pair<Int, Int>) = get(coords.first)[coords.second]

fun Seats.step(): Seats = mapIndexed { i, l -> l.mapIndexed { j, c ->
    val n = (i to j).neighbours(size, l.size)
    when {
        c == Tile.Empty && n.none { get(it) == Tile.Occupied } -> Tile.Occupied
        c == Tile.Occupied && n.count { get(it) == Tile.Occupied } >= 4 -> Tile.Empty
        else -> c
    }
} }

fun Seats.stuff() = mapIndexed { i, l -> l.mapIndexed { j, c ->
    val n = (i to j).neighbourLines(size, l.size).mapNotNull { nn ->
        nn.firstOrNull { get(it) != Tile.Floor }
    }
    when {
        c == Tile.Empty && n.none { get(it) == Tile.Occupied } -> Tile.Occupied
        c == Tile.Occupied && n.count { get(it) == Tile.Occupied } >= 5 -> Tile.Empty
        else -> c
    }
} }

fun Seats.step(n: Int): Seats = if (n > 0) step().step(n - 1) else this
fun Seats.stuff(n: Int): Seats = if (n > 0) stuff().stuff(n - 1) else this

fun Seats.findStable(): Seats {
    var s = this
    var i = 0
    do {
        s = s.step()
        i += 1
    } while (s.step() != s)
    return s
}

fun Seats.findStableStuff(): Seats {
    var s = this
    var i = 0
    do {
        s = s.stuff()
        i += 1
    } while (s.stuff() != s)
    return s
}


fun Seats.count(tile: Tile) = sumBy { l -> l.count { it == tile } }

class Day11Tests {
    @Test
    fun testExamples() {
        val seats = stepExamples[0].parseSeats()
        assertEquals(stepExamples[0], seats.print())
        (0..5).forEach { assertEquals(stepExamples[it], seats.step(it).print()) }
        assertEquals(37, seats.findStable().count(Tile.Occupied))
        (0..6).forEach { assertEquals(stuffExamples[it], seats.stuff(it).print()) }
        assertEquals(26, seats.findStableStuff().count(Tile.Occupied))
    }

    @Test
    fun testInput() {
        val input = getInput(11).parseSeats()
        assertEquals(2183, input.findStable().count(Tile.Occupied))
        assertEquals(1990, input.findStableStuff().count(Tile.Occupied))
    }

    val stepExamples = listOf(
        """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL
        """.trimIndent(),
        """
            #.##.##.##
            #######.##
            #.#.#..#..
            ####.##.##
            #.##.##.##
            #.#####.##
            ..#.#.....
            ##########
            #.######.#
            #.#####.##
        """.trimIndent(),
        """
            #.LL.L#.##
            #LLLLLL.L#
            L.L.L..L..
            #LLL.LL.L#
            #.LL.LL.LL
            #.LLLL#.##
            ..L.L.....
            #LLLLLLLL#
            #.LLLLLL.L
            #.#LLLL.##
        """.trimIndent(),
        """
            #.##.L#.##
            #L###LL.L#
            L.#.#..#..
            #L##.##.L#
            #.##.LL.LL
            #.###L#.##
            ..#.#.....
            #L######L#
            #.LL###L.L
            #.#L###.##
        """.trimIndent(),
        """
            #.#L.L#.##
            #LLL#LL.L#
            L.L.L..#..
            #LLL.##.L#
            #.LL.LL.LL
            #.LL#L#.##
            ..L.L.....
            #L#LLLL#L#
            #.LLLLLL.L
            #.#L#L#.##
        """.trimIndent(),
        """
            #.#L.L#.##
            #LLL#LL.L#
            L.#.L..#..
            #L##.##.L#
            #.#L.LL.LL
            #.#L#L#.##
            ..L.L.....
            #L#L##L#L#
            #.LLLLLL.L
            #.#L#L#.##
        """.trimIndent()
    )

    val stuffExamples = listOf(
        """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL
        """.trimIndent(),
        """
            #.##.##.##
            #######.##
            #.#.#..#..
            ####.##.##
            #.##.##.##
            #.#####.##
            ..#.#.....
            ##########
            #.######.#
            #.#####.##
        """.trimIndent(),
        """
            #.LL.LL.L#
            #LLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLL#
            #.LLLLLL.L
            #.LLLLL.L#
        """.trimIndent(),
        """
            #.L#.##.L#
            #L#####.LL
            L.#.#..#..
            ##L#.##.##
            #.##.#L.##
            #.#####.#L
            ..#.#.....
            LLL####LL#
            #.L#####.L
            #.L####.L#
        """.trimIndent(),
        """
            #.L#.L#.L#
            #LLLLLL.LL
            L.L.L..#..
            ##LL.LL.L#
            L.LL.LL.L#
            #.LLLLL.LL
            ..L.L.....
            LLLLLLLLL#
            #.LLLLL#.L
            #.L#LL#.L#
        """.trimIndent(),
        """
            #.L#.L#.L#
            #LLLLLL.LL
            L.L.L..#..
            ##L#.#L.L#
            L.L#.#L.L#
            #.L####.LL
            ..#.#.....
            LLL###LLL#
            #.LLLLL#.L
            #.L#LL#.L#
        """.trimIndent(),
        """
            #.L#.L#.L#
            #LLLLLL.LL
            L.L.L..#..
            ##L#.#L.L#
            L.L#.LL.L#
            #.LLLL#.LL
            ..#.L.....
            LLL###LLL#
            #.LLLLL#.L
            #.L#LL#.L#
        """.trimIndent()
    )
}
