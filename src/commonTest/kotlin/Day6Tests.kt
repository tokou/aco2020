import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Tests {
    @Test
    fun testExamples() {
        val input = """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent()
        assertEquals(11, countAnswersAnyone(input))
        assertEquals(6, countAnswersEveryone(input))
    }

    @Test
    fun testInput() {
        assertEquals(6768, countAnswersAnyone(getInput(6)))
        assertEquals(3489, countAnswersEveryone(getInput(6)))
    }

}
