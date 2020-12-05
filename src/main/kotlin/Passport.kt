
data class Passport(val fields: Map<String, String>) {

    fun isValid(): Boolean = fields.size == 8 || (fields.size == 7 && !fields.containsKey("cid"))

    fun isReallyValid(): Boolean = isValid() && fields.all { (k, v) -> when (k) {
        "byr" -> v.isYearIn(1920..2002)
        "iyr" -> v.isYearIn(2010..2020)
        "eyr" -> v.isYearIn(2020..2030)
        "hgt" -> when (v.takeLast(2)) {
            "cm" -> v.dropLast(2).isInRange(150..193)
            "in" -> v.dropLast(2).isInRange(59..76)
            else -> false
        }
        "hcl" -> "#[a-f0-9]{6}".toRegex().matches(v)
        "ecl" -> listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(v)
        "pid" -> v.length == 9 && v.toIntOrNull() != null
        "cid" -> true
        else -> false
    } }

    private fun String.isYearIn(range: IntRange) = length == 4 && range.contains(toIntOrNull())
    private fun String.isInRange(range: IntRange) = range.contains(toIntOrNull())

    companion object {
        fun parse(fields: Map<String, String>): Passport = Passport(fields)
    }
}

fun String.passportFields() = split("\n\n").map { p -> p
    .split("([\n ])".toRegex())
    .map { f ->
        val v = f.split(":")
        v[0] to v[1]
    }.toMap()
}
