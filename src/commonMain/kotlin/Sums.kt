
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

fun sumsInPreamble(longs: List<Long>, preambleSize: Int = 25): List<Pair<Boolean, Long>> =
    longs.dropLast(preambleSize).mapIndexed { i, _ ->
        val preamble = longs.drop(i).take(preambleSize)
        val n = longs[i + preambleSize]
        try {
            pairThatSumsTo(preamble, n)
            true to n
        } catch (e: NoSuchElementException) {
            false to n
        }
    }

fun List<Pair<Boolean, Long>>.firstFalse() = first { !it.first }.second

fun sumsContiguousTo(longs: List<Long>, sum: Long): List<Long> {
    var i = 0
    var j = 1
    var s = longs[i] + longs[j]
    while (s != sum) {
        if (s < sum) {
            j += 1
            s += longs[j]
        } else if (s > sum) {
            s -= longs[i]
            i += 1
        }
    }
    return longs.subList(i, j + 1)
}

fun List<Long>.sumMinMax() = (minOrNull() ?: 0) + (maxOrNull() ?: 0)
