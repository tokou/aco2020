
expect fun getInput(day: Int): String
expect fun getLines(day: Int): List<String>

fun getInts(day: Int): List<Int> = getLines(day).map(String::toInt)
