fun main() {

    fun part1(input: List<String>): Int {
        return input.map {it.split(",","-")}
            .map{elem -> elem.map{it.toInt()}}
            .filter {elvesRange -> (elvesRange[0] >= elvesRange[2] && elvesRange[1] <= elvesRange[3])
                    || (elvesRange[0] <= elvesRange[2] && elvesRange[1] >= elvesRange[3])}
            .size
    }

    fun part2(input: List<String>): Int {
        return input.map {it.split(",","-")}
            .map{elem -> elem.map{it.toInt()}}
            .filter {elvesRange -> ((elvesRange[0] <= elvesRange[2] && elvesRange[1] >= elvesRange[2])
                    || (elvesRange[0] >= elvesRange[2] && elvesRange[0] <= elvesRange[3])
                    || (elvesRange[0] >= elvesRange[2] && elvesRange[1] <= elvesRange[3])
                    || (elvesRange[0] <= elvesRange[2] && elvesRange[1] >= elvesRange[3]))
            }
            .size
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}