import java.lang.Integer.max

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

        var maxScore = 0

        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->

                if ((1..input.size-2).contains(i) && (1..row.length-2).contains(j)) {
                    // north
                    var northScore = 1
                    if (i > 0) {
                        for (idx in i - 1 downTo 1) {
                            if (input[idx][j].digitToInt() < input[i][j].digitToInt()) {
                                northScore++
                            } else {
                                break
                            }
                        }
                    }

                    // south
                    var southScore = 1
                    if (i < input.size - 1) {
                        for (idx in i + 1..input.size - 2) {
                            if (input[idx][j].digitToInt() < input[i][j].digitToInt()) {
                                southScore++
                            } else {
                                break
                            }
                        }
                    }

                    // west
                    var westScore = 1
                    if (j > 0) {
                        for (idx in j - 1 downTo 1) {
                            if (input[i][idx].digitToInt() < input[i][j].digitToInt()) {
                                westScore++
                            } else {
                                break
                            }
                        }
                    }

                    // east
                    var eastScore = 1
                    if (j < input[i].length - 1) {
                        for (idx in j + 1..input[i].length - 2) {
                            if (input[i][idx].digitToInt() < input[i][j].digitToInt()) {
                                eastScore++
                            } else {
                                break
                            }
                        }
                    }
                    
                    val thisScore = northScore * southScore * eastScore * westScore
                    maxScore = max(maxScore, thisScore)
                }
            }
        }
        return maxScore
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
