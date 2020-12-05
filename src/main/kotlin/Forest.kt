
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
