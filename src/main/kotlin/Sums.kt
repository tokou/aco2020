
fun pairThatSumsTo2020(input: List<Int>): Pair<Int, Int> = input.allPairs().first { it.first + it.second == 2020 }

fun tripleThatSumsTo2020(input: List<Int>): Triple<Int, Int, Int> =
    input.allTriples().first { it.first + it.second + it.third == 2020 }

fun List<Int>.allPairs(): List<Pair<Int, Int>> = flatMapIndexed { i, a -> drop(i + 1).map { b -> a to b } }

fun List<Int>.allTriples(): List<Triple<Int, Int, Int>> = flatMapIndexed { i, a ->
    drop(i + 1).flatMapIndexed { j, b ->
        drop(j + 1).map { c ->
            Triple(a, b, c)
        }
    }
}

fun String.parseInts(): List<Int> = split("\n").map(String::toInt)
