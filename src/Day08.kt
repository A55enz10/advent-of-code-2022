fun main() {


    fun String.isVisibleFromSides(idx: Int): Boolean {
        return this.substring(0,idx).all { it.digitToInt() < this[idx].digitToInt() }
                || this.substring(idx+1).all { it.digitToInt() < this[idx].digitToInt() }
    }
    
    fun part1(input: List<String>): Int {
        var count = 0
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                // borders are always visible
                if (!(1..input.size-2).contains(i) || !(1..row.length-2).contains(j)) {
                    count++
                } else {
                    var column = ""
                    input.forEach { column += it[j] }
                    if (row.isVisibleFromSides(j) || column.isVisibleFromSides(i)) count++
                }
            }
        }
    return count
}

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
