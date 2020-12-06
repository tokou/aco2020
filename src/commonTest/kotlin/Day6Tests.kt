import kotlin.test.Test
import kotlin.test.assertEquals

fun countAnswersAnyone(input: String) = input.split("\n\n").sumBy {
    it.split("\n").flatMap { it.toList() }.toSet().count()
}

fun countAnswersEveryone(input: String) = input.split("\n\n").sumBy {
    val groupAnswers = it.split("\n")
    val allPossible = groupAnswers.flatMap { it.toList() }.toSet()
    val counts = allPossible.map { p -> p to groupAnswers.count { a -> a.contains(p) } }
    val result = counts.filter { it.second == groupAnswers.size }.count()
    result
}

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
