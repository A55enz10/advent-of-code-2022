fun main() {

    fun part1(input: List<String>): String {
        val supplies = getInitialStacks(input)
        val instructions = getInstructions(input)
        
        instructions.forEach { 
            val quantity = it[0]
            val fromStack = it[1]-1
            val toStack = it[2]-1
            
            for (i in 1..quantity) {
                supplies[toStack].add(supplies[fromStack].removeLast())
            }
        }
        var ret = ""
        supplies.forEach { 
            ret += it.removeLast()
        }
        return ret
    }

    fun part2(input: List<String>): String {
        val supplies = getInitialStacks(input)
        val instructions = getInstructions(input)

        instructions.forEach {
            val quantity = it[0]
            val fromStack = it[1]-1
            val toStack = it[2]-1

            val buffer = ArrayDeque<String>()
            
            for (i in 1..quantity) {
                buffer.add(supplies[fromStack].removeLast())
            }
            for (i in 1..quantity) {
                supplies[toStack].add(buffer.removeLast())
            }
        }
        var ret = ""
        supplies.forEach {
            ret += it.removeLast()
        }
        return ret
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun getInitialStacks(input: List<String>): List<ArrayDeque<String>> {
    val stackBlock =
        input.subList(0, input.indexOf("") - 1)
            .map{ row ->
                row.chunked(4).map {
                    it.trim().replace("[", "").replace("]", "")
                }
            }

    val stackSize = input[input.indexOf("")-1].replace(" ", "").length

    val stacks = mutableListOf(ArrayDeque<String>())
    for (loop in 1 until stackSize) {
        stacks.add(ArrayDeque())
    }

    for (i in stackBlock.size - 1 downTo 0) {
        stackBlock[i].forEachIndexed { j, s ->
            if (s.isNotEmpty()) stacks[j].add(s)
        }
    }

    return stacks
}

fun getInstructions(input: List<String>): List<List<Int>> {
    return input
        .subList(input.indexOf("") + 1, input.size)
        .map { row ->
            row.replace("move ", "")
                .replace("from ", "")
                .replace("to ", "")
                .split(" ")
                .map { it.toInt() }
        }
}