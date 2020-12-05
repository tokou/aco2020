
data class Seat(val position: Pair<Int, Int>) {

    val id: Int get() = position.first * 8 + position.second

    companion object {
        fun encoded(encoding: String): Seat {
            val position = encoding.fold((0..127) to (0..7)) { r, c -> when (c) {
                'F' -> with(r.first) { first..(first + last)/2 } to r.second
                'B' -> with(r.first) { ((first + last)/2 + 1)..last } to r.second
                'L' -> r.first to with(r.second) { first..(first + last)/2 }
                'R' -> r.first to with(r.second) { ((first + last)/2 + 1)..last }
                else -> r
            } }.run { first.first to second.last }
            return Seat(position)
        }
    }
}

fun String.seats() = split("\n").map { Seat.encoded(it) }
