import kotlin.test.Test
import kotlin.test.assertEquals

data class Forest(val height: Int, val width: Int, val trees: Set<Pair<Int, Int>>) {

    fun downSlope(slope: Pair<Int, Int> = 1 to 3): List<Pair<Int, Int>> {
        var current = 0 to 0
        val encountered = mutableListOf<Pair<Int, Int>>()
        while (current.first < height) {
            if (trees.contains(current.first to (current.second % width))) {
                encountered.add(current)
            }
            current = (current.first + slope.first) to (current.second + slope.second)
        }
        return encountered
    }

    companion object {
        fun parse(string: String): Forest {
            val lines = string.split("\n")
            val coords = lines.flatMapIndexed { i, l ->
                l.mapIndexed { j, c -> c to j }
                    .filter { it.first == '#' }
                    .map { i to it.second }
            }.toSet()
            return Forest(lines.size, lines.first().length, coords)
        }
    }
}

class Day3Tests {
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
        val forest = Forest.parse(day3)
        assertEquals(242, forest.downSlope().size)
        assertEquals(2265549792, slopes.fold(1L) { acc, s -> acc * forest.downSlope(s).size })
    }

    val slopes = listOf(1 to 1, 1 to 3, 1 to 5, 1 to 7, 2 to 1)

    val day3 = """
        ...........#..#.#.###....#.....
        ...#..#...........#.#...#......
        #.....#..#........#...#..##....
        ..#...##.#.....#.............#.
        #.#..#......#.....#....#.......
        .....#......#..#....#.....#....
        .......#.#..............#......
        .....#...#..........##...#.....
        ...#....#.#...#.#........#...#.
        ..#.........###.......##...##..
        .#....#...........#........#..#
        ..#.............##.............
        ..#.##.#....#................#.
        .....##.#.......#....#...#.....
        ......#.#....##................
        ..#..........###..#..#.#..#....
        ....#..............#....##..#.#
        .#.........#.#....#.#.#....#...
        ..#.....#......##.#....#.......
        ..#.#....#..#.#...##....###....
        ...#......##...#........#.#..#.
        .##.#.......##....#............
        ...##..#.#............#...#.#..
        .##...##.#..#..................
        ..#......##......#......##.....
        .....##...#..#...#.........#...
        .##.#.....#..#..#.##....##....#
        ..#.#......#.......##..........
        ......................#......##
        ##.#...#.................#.#.#.
        ......#.#..........#.....##.#..
        #.#......#.....#...........#...
        .....#...#.......#..#..#.#...#.
        ...........#......#.#...#......
        ....##...##...........#......#.
        .........#.##..................
        ......#...#....#......##.##...#
        ......#...#.#########......#...
        .......#.#...#.......#..#......
        ............#...#...#.###...##.
        ...........#..........#...#....
        ...#..#.#................#.#..#
        ..#....#.....#.............#.#.
        ....##......#........#....#....
        ........##...............#....#
        ........#..#...#..............#
        ...#....#.#...#..#...#....#.#.#
        .........#.......#....##.......
        #.##..............#.#........##
        ......................###......
        .........#....##..##....#.#.#..
        .#...##.#.#...#....##..#.....#.
        ....................#.#......#.
        .#..#.......................#..
        ..#..#.............#..#....#...
        ...#...#..#...#...#.#..#.##....
        ........#....#...#....#........
        .#.....#........#.........#...#
        ...#......#..#..#..####..#.....
        #....#..............#.##.......
        .#....#.............##...#.....
        ....#...#.##........##......#..
        ##....#...#.......#..#........#
        ....##........................#
        ..................#..#.........
        ..#....#........#..#.......#...
        #...#..#....#...##...........#.
        .........#..#..#.#.##..........
        ....#.#..#.....#..#.#.#.#..#.##
        ##................#.##.....#...
        .#.....###..#..#..#.....#....##
        ...#...........#..........####.
        .#.....#....#......#.##..#.#...
        ..#...##....#................#.
        ........#.......#......#.#.....
        ....#.#.#.#....#...#......#..#.
        ...........#......#..#.........
        ###...##......##.#..#....##....
        ##....##.........#..#....###...
        #.#.....#....#......#...#..##..
        #....##.#..............#.##....
        .#........#.#.........#...#....
        ......................#......#.
        ........#..#..##.....#..#.#....
        ..#...###.................#..#.
        ...#...#............#..........
        .##.......#..#.........#....#..
        .#..............#....#....##...
        ...............##..#.#.......##
        .#.....#....#...#..#.......#..#
        #..#.............#....#......#.
        .....#.#......#.........###..#.
        .#...#.#...............#....#..
        #......#.............#.........
        .#.##.#.####...#..#.##..#.....#
        .....#......#..#...#.......#...
        #........###...#.....#..#.....#
        ....#.#.....#...#..........#...
        ...#.#.......#.#......#..#.##..
        ..#..........#.#..#.......#.#..
        #...#.#..............#...###.#.
        ...#..#...#............###.....
        ..#..#...#.#............#..#...
        .###.#.....................#..#
        ....#....#..#.....##.##........
        #....#....#.#..#.........#.....
        .#.....##....#............##..#
        #....#.#...#...#..#.#......#...
        #.....##.....##.....##.#...##..
        ...##...#..#..####.#........#..
        .........#...#......##..##..#..
        ..#.....###.#..#..####.#.......
        .......#......#...#..##....#...
        .#.....#.#.#..#....#...#..##...
        ..........#.#...#...#.#..#.....
        ....#.....#........#.....##..#.
        ..#.#.##.........#...##.....##.
        .........#.##....#............#
        ............##.....#.......#.#.
        ......#.....#...#..#..###......
        ##.....#.......#...##.#....#...
        ...........##......#..##...#.#.
        ..#.#.#.#...#.......#....#...#.
        #.............#.....#.#...###..
        ##....#.......#.....#..##.#....
        ...#.......#....#.........##...
        ......#.......#......##.##.....
        ..#......#...#.#........#......
        ....#.#....#.#.##......#.....#.
        #......#.........#..#....#.....
        ........#..#....##.....#.......
        #......##....#.##....#......#..
        ..#.......#............##.....#
        ...........#...#...........#...
        #.......#...#....#....#...#.#.#
        ..###..#........#........#.....
        ..#..###...........#.#......#..
        .#...........#......#..........
        .#.......#.....#.......#.......
        ..#......##.#............#....#
        #..........#.....#.#..#........
        .....#...##.##.......#......#..
        ..........#.....#........#.#.#.
        ....#......#.....#......#.#....
        .........#.#.#..#...##....#...#
        .........#.......#...##...#.#..
        .##........#...............#...
        .......#....#...........##.....
        .........###......#.........#.#
        ......#.......#...#..........#.
        ...#.#..........##......#...#..
        #.......#.#..........#.........
        ................#..#......#..##
        .....#...#....#.#.....#........
        #.....#....#...........#....#..
        #....#.#..#...##....#...##.#...
        ...#.....#......#.#....#..#..#.
        ..#................#...#.#..##.
        ..........#..............#..#.#
        .....##.....#..#.###...........
        ....#.#......#.#...........#...
        .#....#.#.........##.#....#....
        .#.#........#........###....#..
        ##.#................#...#..#...
        .......#......##..#.....#..#.#.
        ...#............#......###...##
        #.#...........#.........#......
        .....#.#.#.................#...
        ....#..............#...#.#.....
        ...#.#.....##..#...............
        .#..##...#....##.....###..#....
        ...............#...#...#.#.###.
        .###....#.....#...#.#......#...
        ...#..#.....#.......#..##.#....
        ...........#..#....##..#...#...
        ...#...#..........#.......##.#.
        ............#.#.......#........
        ....#.........#.....#..........
        ...#.###.##..#...##..####..#..#
        .#.#...#..#...................#
        .....#..#.....##..#............
        ....#......#...##..#.##........
        ...#...............#..#.....##.
        ...#......#.........#.#..##....
        .#....#.##.......#......#......
        ....#.......#....#..........#..
        #.#.#....###.#.#.............#.
        ..##..###........#.#..#.#..#...
        ......#.#............##.#...###
        .........#.#....#####.....##...
        ............##......#.#..#.....
        ...#.....#.....###....#........
        ##..........####.##.#.#........
        ....................##.....##.#
        #.#............#........#......
        ....#...##.....#......#....#...
        ...###.#..##..................#
        ..###......#..............#.#.#
        .#...#...........#....#........
        ....#............#..#..#.#.....
        ...#.........#.#.........#.####
        ..#...#...#...#...........#....
        ...............#.#......##..#..
        #....#.#.......#.#..#......#..#
        ........#.#....#..#...#..#...#.
        ...#..#.......#...........#....
        ...........#.......#...........
        .#......#................#.....
        ....#.#.#...#......#..#...#....
        ................#.#.#....#.....
        .........................##..#.
        .#...........#............##...
        #...............#.....##.#.#..#
        .........#.......###....#.....#
        ....##...#...#.....#..#........
        ........#.....#..#.#.#...#..#..
        ......#.......#.#.........#.#..
        #......#............#...#....#.
        #..##...#..#................#..
        .##...#...#.....#.##.......#..#
        .......#.##........##..##......
        ##.#..##...............#.....#.
        ......#....#..##...#......###.#
        #........##..#....#.#......#...
        .#......##.#...#.#...#.........
        .#.#...#..#.............#......
        .##..........#..........#......
        .#.....#.....#..............#.#
        ..#.........#..#.#.....#.#....#
        ..#.##..............##...#..###
        ....................#..........
        ......###..#..#...........#....
        ..#..........#.......#...#.....
        ...#......#......#.............
        ....##..............#.#.....#..
        ........#.#......#..#........##
        .............#...#.#.........##
        ...###...#..........##.......#.
        .#..........#...##..#.#.....#..
        ##...#.........#...............
        ......#....#....#.....#.....#..
        ..........#....#...#...#..#...#
        ...##....#.#.#..#...##.........
        #......#.#...##.###...#....#...
        ##.......##.#......##..#...#...
        ......#.............#.##.....##
        #.......###....####.#...##....#
        ..#...#..#.......#..........#..
        #.....#..#..#..#.##...###...#..
        .....##.#..#..#..#.#....#...#..
        ..#...#..................##....
        ....#.#........##..............
        #...#.......##...#...#.#.......
        ..#...#........##....#.#.......
        ..........###...###...#......#.
        #.....#..###...##...##..#..#..#
        ..#.....##.....#.......##..#.#.
        ........#........#.........#...
        .................#....#.......#
        .......#...#.....#...#.#.......
        ....##...............#...##...#
        .##...#................#...#...
        .............#.................
        .#..#....#....#.#....#.........
        .#.#..#..........#.......#.....
        .....##.....##...#..#..........
        #...#.#.........#......#..#....
        ........#....#...#....#.#.##...
        ....#..#........#...#...#......
        .#..#.....#.#...#.........#....
        .#..#..#...........#..#....#...
        ....###.............#..#.......
        #......#..#..##..........#.#...
        #..#..#.##..#...#.#.#..........
        ....###......#.##.....#....#...
        .##..#...#......##.#...........
        ..#..#.......#.....#.##....#.#.
        .......#.#.#........#....##....
        ..##...#....#...............###
        #..##..#...........#.#....##...
        ...##..#.....................#.
        ###......#....#....###..#...##.
        .........##............#..#...#
        ..#..........#...#.#.#......#.#
        .......#.....##..##......#.##..
        #..........#.....##.#..........
        #.......#.#...#...#....#.......
        #...#.....##.......#.#..#.#.#..
        .........#.#.#..#..#...#.###...
        .................##...#....#...
        ###.......#..........##...#....
        #.#..#.........#....##.#.......
        ......#.#.....#........#.......
        .......#.#........#......#.#..#
        ..............#..#...##....#..#
        #...........#...##.....#..#.#..
        ..#....#..#.#.#...#..#....#.#..
        ...##.#.....#..#...##..#.....#.
        ..#.#................#........#
        ......#...#.............#......
        .##............#....#...#..#...
        ....#...#...........#.......#..
        .###..#.......#.............#.#
        .#.#....#.#...........#.#......
        ...#.........#.........#..#....
        ...#..........#.#.....#.#......
        .....#........#....##......#...
        ..#.#.#......#..#.#......#....#
        .#.#..#................#.#.....
        .#.#.........##...#.......#.#.#
        #..#.....#...#..#...........#..
        ..##......####......#..#....###
        #.....###....#.#........#..#..#
        ..##.#...#.#..##..........#..#.
        #.........#.#.............#...#
        ...#.#...#...#.#.#....##.......
        ##.##...#.....#...#...........#
        ....#........#.#.....#.........
        .................##..#..##...##
        .....##....#...#...#.....#..#..
        ....#...#........#............#
        ..#...........##....#...#...##.
        .....#......#.........#..##.#..
    """.trimIndent()
}
