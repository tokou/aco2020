import kotlin.test.Test
import kotlin.test.assertEquals

sealed class Expr {
    abstract fun run(): Long
    sealed class Operator: Expr() {
        abstract val operands: Pair<Expr, Expr>
        data class Add(override val operands: Pair<Expr, Expr>) : Operator() {
            override fun toString(): String = "(${operands.first} + ${operands.second})"
            override fun run(): Long = operands.first.run() + operands.second.run()
        }
        data class Mul(override val operands: Pair<Expr, Expr>) : Operator() {
            override fun toString(): String = "(${operands.first} * ${operands.second})"
            override fun run(): Long = operands.first.run() * operands.second.run()
        }
    }
    data class Operand(val value: Long): Expr() {
        override fun toString(): String = "$value"
        override fun run(): Long = value
    }
}

fun evaluateExpression(expression: String, precedence: Boolean = false): Long {
    val expressions = mutableListOf<Expr>()
    val operators = mutableListOf<Char>()
    val ops = listOf('*', '+')

    fun addExpr() {
        val o = operators.removeLast()
        val op2 = expressions.removeLast()
        val op1 = expressions.removeLast()
        val exp = when (o) {
            '+' -> Expr.Operator.Add(op1 to op2)
            '*' -> Expr.Operator.Mul(op1 to op2)
            else -> error("wtf")
        }
        expressions.add(exp)
    }

    for (c in expression.replace(" ", "")) { when (c) {
        '(' -> operators.add(c)
        in '0'..'9' -> expressions.add(Expr.Operand(c.toString().toLong()))
        '+', '*' -> {
            while (
                operators.isNotEmpty() &&
                operators.last() in ops &&
                if (precedence) ops.indexOf(operators.last()) >= ops.indexOf(c) else true
            ) {
                addExpr()
            }
            operators.add(c)
        }
        ')' -> {
            while (operators.last() != '(') addExpr()
            operators.removeLast()
        }
        else -> error("Malformed expression '$c'")
    } }
    while (operators.isNotEmpty()) addExpr()
    return expressions.first().run()
}

class Day18Tests {

    @Test
    fun testExamples() {
        assertEquals(71, evaluateExpression("1 + 2 * 3 + 4 * 5 + 6"))
        assertEquals(51, evaluateExpression("1 + (2 * 3) + (4 * (5 + 6))"))
        assertEquals(26, evaluateExpression("2 * 3 + (4 * 5)"))
        assertEquals(437, evaluateExpression("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        assertEquals(12240, evaluateExpression("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        assertEquals(13632, evaluateExpression("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
        assertEquals(231, evaluateExpression("1 + 2 * 3 + 4 * 5 + 6", true))
        assertEquals(51, evaluateExpression("1 + (2 * 3) + (4 * (5 + 6))", true))
        assertEquals(46, evaluateExpression("2 * 3 + (4 * 5)", true))
        assertEquals(1445, evaluateExpression("5 + (8 * 3 + 9 + 3 * 4 * 3)", true))
        assertEquals(669060, evaluateExpression("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", true))
        assertEquals(23340, evaluateExpression("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", true))
    }

    @Test
    fun testInput() {
        val input = getLines(18)
        assertEquals(701339185745, input.map { evaluateExpression(it) }.sum())
        assertEquals(4208490449905, input.map { evaluateExpression(it, true) }.sum())
    }
}
