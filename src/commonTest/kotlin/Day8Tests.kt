import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Tests {
    @Test
    fun testExamples() {
        val input = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent()
        val program = input.parseProgram()
        assertEquals(5, program.run().second)
        assertEquals(8, program.fix())
    }

    @Test
    fun testInput() {
        val program = getInput(8).trimEnd().parseProgram()
        assertEquals(2025, program.run().second)
        assertEquals(2001, program.fix())
    }
}
