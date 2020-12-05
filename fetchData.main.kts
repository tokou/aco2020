#!/usr/bin/env kotlin

import java.io.BufferedInputStream
import java.io.FileReader
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess

if (args.isEmpty()) {
    println("Missing argument: day")
    exitProcess(1)
}

val day = args.first()

val properties = Properties().apply {
    load(FileReader("local.properties"))
}

if (!properties.containsKey("session")) {
    println("Missing property: session")
    exitProcess(1)
}

val cookie = "session=${properties["session"]}"

val url = URL("https://adventofcode.com/2020/day/$day/input")

url.openConnection().run {
    setRequestProperty("Cookie", cookie)
    val fis = BufferedInputStream(getInputStream())
    val path = Paths.get("src/test/resources/day$day.txt")
    Files.copy(fis, path)
}
