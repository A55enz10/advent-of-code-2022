import java.util.*

enum class Sign {ROCK, PAPER, SCISSORS}

fun main() {
    
    fun part1(input: List<String>): Int {

        val valueToSignP1 = mapOf("A" to Sign.ROCK, "B" to Sign.PAPER, "C" to Sign.SCISSORS)
        val valueToSignP2 = mapOf("X" to Sign.ROCK, "Y" to Sign.PAPER, "Z" to Sign.SCISSORS)

        val game = Game()
        input.forEach {
            val signs = it.split(" ")
            game.playRound(valueToSignP1[signs[0]], valueToSignP2[signs[1]])
        }

        return game.scorePlayer2
    }

//    fun part2(input: List<String>): Int {
//        return 0
//    }
    
    val input = readInput("Day02")
    println(part1(input))
//    println(part2(input))

}

class Game {
    private val pointPerSign: Map<Sign, Int> = mapOf(Sign.ROCK to 1, Sign.PAPER to 2, Sign.SCISSORS to 3)

    private var comparatorWheel = listOf(Sign.ROCK, Sign.PAPER, Sign.SCISSORS)
    var scorePlayer1 = 0
    var scorePlayer2 = 0

    fun playRound(signPlayer1: Sign?, signPlayer2: Sign?) {
        scorePlayer1 += pointPerSign[signPlayer1]?: 0
        scorePlayer2 += pointPerSign[signPlayer2]?: 0

        // rotate the wheel until the player1 sign is center
        Collections.rotate(comparatorWheel, 1-comparatorWheel.indexOf(signPlayer1))
        when (comparatorWheel.indexOf(signPlayer1).compareTo(comparatorWheel.indexOf(signPlayer2))) {
            -1 -> scorePlayer2 += 6
            0 -> {
                scorePlayer1 += 3
                scorePlayer2 += 3
            }
            1 -> scorePlayer1 += 6
        }
    }
}


