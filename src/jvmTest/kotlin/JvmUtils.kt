import java.io.File

val resourcesDir = File("src/commonTest/resources")

actual fun getInput(day: Int): String = File(resourcesDir, "day$day.txt").readText()
actual fun getLines(day: Int): List<String> = File(resourcesDir, "day$day.txt").readLines()
