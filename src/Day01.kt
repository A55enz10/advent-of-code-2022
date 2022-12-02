fun main() {
    fun part1(input: List<String>): Int {
        return getElvesKcalList(input).maxOrNull()!!
    }

    fun part2(input: List<String>): Int {
        val elvesKcal = getElvesKcalList(input)
        elvesKcal.sortDescending()
        return elvesKcal[0] + elvesKcal[1] + elvesKcal[2]
    }
    
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))

    check(part1(input) == 71934)
    check(part2(input) == 211447)
}

fun getElvesKcalList(input: List<String>): MutableList<Int> {
    val elvesKcal = mutableListOf<Int>()
    var sum = 0
    input.forEachIndexed { index, s ->
        if (s.isEmpty()) {
            elvesKcal.add(sum)
            sum = 0
        } else {
            sum += s.toInt()
            if (input.size == index+1) {
                elvesKcal.add(sum)
            }
        }
    }
    return elvesKcal
}
