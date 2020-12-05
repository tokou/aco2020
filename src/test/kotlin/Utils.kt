import java.io.File

val resourcesDir = File("src/test/resources")

fun getInput(day: Int): String = File(resourcesDir, "day$day.txt").readText()
fun getLines(day: Int): List<String> = File(resourcesDir, "day$day.txt").readLines()
fun getInts(day: Int): List<Int> = File(resourcesDir, "day$day.txt").readLines().map(String::toInt)
