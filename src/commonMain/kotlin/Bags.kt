
typealias Node = String
typealias Weight = Int
typealias Vertex = Triple<Node, Node, Weight>
typealias Graph = Set<Vertex>

fun String.parseGraph(): Graph = split("\n").flatMap { line ->
    val (from, remaining) = line.dropLast(1).split(" bags contain ")
    val destinations = remaining
        .split(", ")
        .map { it.substringBeforeLast(" bag")  }
        .filterNot { it == "no other" }
        .map { it.substringBefore(" ").toInt() to it.substringAfter(" ") }
    destinations.map { Triple(from, it.second, it.first) }
}.toSet()

fun Graph.countAncestors(node: Node): Int {
    val queue = mutableSetOf(node)
    val counted = mutableSetOf<Node>()
    while (queue.isNotEmpty()) {
        val n = queue.first()
        queue.remove(n)
        val ancestors = filter { it.second == n }.map { it.first }.toSet()
        queue.addAll(ancestors.filterNot { counted.contains(it) })
        counted.addAll(ancestors)
    }
    return counted.size
}

fun Graph.countContents(node: Node): Int =
    filter { it.first == node }.sumBy { it.third * (1 + countContents(it.second)) }
