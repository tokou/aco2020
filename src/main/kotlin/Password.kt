
data class Password(val range: IntRange, val letter: Char, val password: String) {
    fun isValid(): Boolean = range.contains(password.count { it == letter })
    fun isReallyValid(): Boolean =
        (password[range.first - 1] == letter).xor(password[range.last - 1] == letter)

    companion object {
        fun parse(string: String): Password {
            val (range, letter, password) = string.split(" ")
            val (lower, upper) = range.split("-").map(String::toInt)
            return Password(IntRange(lower, upper), letter[0], password)
        }
    }
}

fun String.parsePasswords(): List<Password> = split("\n").map { Password.parse(it) }
