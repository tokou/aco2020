import org.w3c.xhr.XMLHttpRequest

fun readFile(day: Int): String = with(XMLHttpRequest()) {
    open("GET", "test/resources/day$day.txt", false)
    send()
    responseText
}

actual fun getInput(day: Int): String = readFile(day)
actual fun getLines(day: Int): List<String> = readFile(day).split("\n")
