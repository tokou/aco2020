import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Tests {
    @Test
    fun testExamples() {
        val input = "FBFBBFFRLR"
        val seat = Seat.encoded(input)
        assertEquals(357, seat.id)
    }

    @Test
    fun testInput() {
        val seats = getLines(5).map { Seat.encoded(it) }
        assertEquals(826, seats.maxByOrNull { it.id }?.id)
        val filledPositions = seats.map { it.position }
        val allPositions = (0..127).flatMap { x -> (0..7).map { y -> x to y  } }.toSet()
        val potentiallyVacant = allPositions.minus(filledPositions).map { Seat(it) }
        val adjacentIds = potentiallyVacant.map { it to listOf(it.id + 1, it.id - 1) }.toMap()
        val filledIds = seats.map { it.id }
        val validSeat = adjacentIds.filter { filledIds.containsAll(it.value) }.keys.toList().first()
        assertEquals(678, validSeat.id)
    }

}
