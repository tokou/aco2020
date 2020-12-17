import kotlin.test.Test
import kotlin.test.assertEquals

typealias Point = Triple<Int, Int, Int>
typealias Space = Set<Point>

data class HyperPoint(val x: Int, val y: Int, val z: Int, val w: Int)
typealias HyperSpace = Set<HyperPoint>

fun Point.neighbours(): Set<Point> {
    val (z, y, x) = this
    return (-1..1).flatMap { dz ->
        (-1..1).flatMap { dy ->
            (-1..1).map { dx ->
                Triple(z + dz, y + dy, x + dx)
            }
        }
    }.toSet() - this
}

fun HyperPoint.neighbours(): Set<HyperPoint> {
    val (x, y, z, w) = this
    return (-1..1).flatMap { dw ->
        (-1..1).flatMap { dz ->
            (-1..1).flatMap { dy ->
                (-1..1).map { dx ->
                    HyperPoint(x + dx, y + dy, z + dz, w + dw)
                }
            }
        }
    }.toSet() - this
}

fun IntRange.extend() = (start - 1)..(endInclusive + 1)

fun List<Int>.range(): IntRange = minOrNull()!!..maxOrNull()!!

fun Space.step(): Space {
    val active = filter { p -> p.neighbours().count { it in this } in 2..3 }
    val coords = map { it.first }.range().extend().flatMap { z ->
        map { it.second }.range().extend().flatMap { y ->
            map { it.third }.range().extend().map { x ->
                Triple(z, y, x)
            }
        }
    }
    val inactive = coords.filterNot { it in this }.filter { p -> p.neighbours().count { it in this } == 3 }
    return (active + inactive).toSet()
}

fun HyperSpace.hyperStep(): HyperSpace {
    val active = filter { p -> p.neighbours().count { it in this } in 2..3 }
    val coords = map { it.w }.range().extend().flatMap { w ->
        map { it.z }.range().extend().flatMap { z ->
            map { it.y }.range().extend().flatMap { y ->
                map { it.x }.range().extend().map { x ->
                    HyperPoint(x, y, z, w)
                }
            }
        }
    }
    val inactive = coords.filterNot { it in this }.filter { p -> p.neighbours().count { it in this } == 3 }
    return (active + inactive).toSet()
}

fun HyperSpace.hyperStep(n: Int): HyperSpace =
    if (n == 0) this
    else hyperStep().hyperStep(n - 1)

fun Space.step(n: Int): Space =
    if (n == 0) this
    else step().step(n - 1)

fun Space.print() {
    map { it.first }.range().forEach { z ->
        println("z=$z")
        map { it.second }.range().forEach { y ->
            map { it.third }.range().forEach { x ->
                if (Triple(z, y, x) in this) print("#") else print(".")
            }
            println()
        }
        println()
    }
}

fun HyperSpace.hyperPrint() {
    map { it.w }.range().forEach { w ->
        print("w=$w, ")
        map { it.z }.range().forEach { z ->
            println("z=$z")
            map { it.y }.range().forEach { y ->
                map { it.x }.range().forEach { x ->
                    if (HyperPoint(x, y, z, w) in this) print("#") else print(".")
                }
                println()
            }
        }
        println()
    }
}

fun String.parseSpace(): Space = lines().flatMapIndexed { j, s ->
    s.withIndex()
        .filter { it.value == '#' }
        .map { Triple(0, j, it.index) }
}.toSet()

fun String.parseHyperSpace(): HyperSpace = lines().flatMapIndexed { j, s ->
    s.withIndex()
        .filter { it.value == '#' }
        .map { HyperPoint(it.index, j, 0, 0) }
}.toSet()

class Day17Tests {

    @Test
    fun testExamples() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent()
        assertEquals(112, input.parseSpace().step(6).size)
        assertEquals(848, input.parseHyperSpace().hyperStep(6).size)
    }

    @Test
    fun testInput() {
        val input = getInput(17)
        assertEquals(391, input.parseSpace().step(6).size)
        assertEquals(2264, input.parseHyperSpace().hyperStep(6).size)
    }
}
