fun main() {
    
    fun charToValue(char: Char): Int {
        var charNum: Int = char.hashCode() - 96
        if (char.isUpperCase()) charNum += 32 + 26
        return charNum
    }
    
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            for (i in 0..it.length/2) {
                val idx = it.indexOf(it[i], it.length / 2)
                if (idx > 0) {
                    sum += charToValue(it[idx])
                    break
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (i in input.indices step 3) {
            for (j in 0 until input[i].length) {
                if (i+2 < input.size 
                    && input[i+1].contains(input[i][j]) 
                    && input[i+2].contains(input[i][j])) {
                    sum += charToValue(input[i][j])
                    break
                }
            }
        }
        return sum
    }
    
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}