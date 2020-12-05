
fun Int.floorMod(mod: Int): Int = ((this % mod) + mod) % mod

fun Pair<Int, Int>.floorMod(mod: Pair<Int, Int>) = first.floorMod(mod.first) to second.floorMod(mod.second)

fun Pair<Int, Int>.toDouble() = first.toDouble() to second.toDouble()

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

operator fun Pair<Double, Double>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

operator fun Pair<Int, Int>.plus(other: Pair<Double, Double>) = first + other.first to second + other.second

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second

operator fun Pair<Int, Int>.minus(other: Pair<Double, Double>) = first - other.first to second - other.second

operator fun Pair<Int, Int>.times(other: Pair<Int, Int>) = first * other.first to second * other.second

operator fun Pair<Int, Int>.times(other: Pair<Double, Double>) = first * other.first to second * other.second

operator fun Pair<Double, Double>.times(other: Pair<Double, Double>) = first * other.first to second * other.second

operator fun Pair<Double, Double>.times(other: Pair<Int, Int>) = first * other.first to second * other.second

operator fun Int.times(other: Pair<Int, Int>): Pair<Int, Int> = other.first * this to other.second * this

operator fun Int.times(other: Pair<Double, Double>): Pair<Double, Double> = other.first * this to other.second * this

operator fun Double.times(other: Pair<Int, Int>): Pair<Double, Double> = other.first * this to other.second * this
