
fun pairThatSumsTo(input: List<Long>, sum: Long = 2020): Pair<Long, Long> = input.allPairs().first { it.first + it.second == sum }

fun tripleThatSumsTo2020(input: List<Long>): Triple<Long, Long, Long> =
    input.allTriples().first { it.first + it.second + it.third == 2020L }

fun List<Long>.allPairs(): List<Pair<Long, Long>> = flatMapIndexed { i, a -> drop(i + 1).map { b -> a to b } }

fun List<Long>.allTriples(): List<Triple<Long, Long, Long>> = flatMapIndexed { i, a ->
    drop(i + 1).flatMapIndexed { j, b ->
        drop(j + 1).map { c ->
            Triple(a, b, c)
        }
    }
}
