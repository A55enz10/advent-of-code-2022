fun main() {
    fun part1(input: List<String>): Int {
        return getElvesKcalList(input).maxOrNull()!!
    }

    fun part2(input: List<String>): Int {
        val elvesKcal = getElvesKcalList(input)
        elvesKcal.sortDescending()
        return elvesKcal.get(0) + elvesKcal.get(1) + elvesKcal.get(2)
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
    val elvesKcal = mutableListOf<Int>(0)
    // ensures that list finished with blank line
    val inputSpace = mutableListOf<String>()
    inputSpace.addAll(input)
    inputSpace.add("")

    var sum: Int = 0
    inputSpace.stream().forEachOrdered({
        if (it.isNullOrEmpty()) {
            elvesKcal.add(sum)
            sum = 0
        } else sum += it.toInt()
    })
    return elvesKcal
}
