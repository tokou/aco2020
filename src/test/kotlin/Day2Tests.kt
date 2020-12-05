import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Tests {
    @Test
    fun testExamples() {
        val input = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent()
        val passwords = input.split("\n").map { Password.parse(it) }
        assertEquals(2, passwords.count(Password::isValid))
        assertEquals(1, passwords.count(Password::isReallyValid))
    }

    @Test
    fun testInput() {
        val input = getLines(2).map { Password.parse(it) }
        assertEquals(467, input.count(Password::isValid))
        assertEquals(441, input.count(Password::isReallyValid))
    }

}
