
enum class Command { nop, acc, jmp }
typealias Instruction = Pair<Command, Int>
typealias Program = List<Instruction>

fun String.parseProgram() = split("\n").map {
    val (i, a) = it.split(" ")
    Command.valueOf(i) to a.toInt()
}

fun Program.run(limit: Int = size): Pair<Boolean, Int> {
    var reg = 0
    var pc = 0
    var counter = 0
    val visited = mutableSetOf<Int>()
    while (counter < limit && pc < size) {
        if (visited.contains(pc)) return false to reg
        visited.add(pc)
        val (cmd, arg) = get(pc)
        when (cmd) {
            Command.nop -> pc += 1
            Command.acc -> {
                reg += arg
                pc += 1
            }
            Command.jmp -> pc += arg
        }
        counter += 1
    }
    return (pc >= size) to reg
}

fun Program.patched(index: Int): Program {
    val mut = toMutableList()
    val old = get(index)
    val new = when (old.first) {
        Command.nop -> Command.jmp to old.second
        Command.jmp -> Command.nop to old.second
        Command.acc -> Command.acc to old.second
    }
    mut[index] = new
    return mut
}

fun Program.fix(): Int? {
    val indices = withIndex()
        .filter { it.value.first != Command.acc }
        .map { it.index }
    for (i in indices) {
        val res = patched(i).run()
        if (res.first) return res.second
    }
    return null
}
