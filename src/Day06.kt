fun main() {

    fun part1(input: List<String>): Int {
        val step = 4
        for (i in 0..input[0].length-step) {
            if (input[0].substring(i,i+step).allUnique()) {
                return i+step
            }
            
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val step = 14
        for (i in 0..input[0].length-step) {
            if (input[0].substring(i,i+step).allUnique()) {
                return i+step
            }

        }
        return 0
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)
