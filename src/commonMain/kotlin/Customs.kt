
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
