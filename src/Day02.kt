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

    fun getValueToPlay(sign1: Sign?, s2: String): Sign? {
        
        val signToWin = mapOf(Sign.SCISSORS to Sign.ROCK, Sign.ROCK to Sign.PAPER, Sign.PAPER to Sign.SCISSORS)
        val signToLose = mapOf(Sign.SCISSORS to Sign.PAPER, Sign.ROCK to Sign.SCISSORS, Sign.PAPER to Sign.ROCK)

        return when (s2) {
            "X" -> signToLose[sign1]
            "Z" -> signToWin[sign1]
            else -> sign1
        }
    }

    fun part2(input: List<String>): Int {
        val valueToSignP1 = mapOf("A" to Sign.ROCK, "B" to Sign.PAPER, "C" to Sign.SCISSORS)

        val game = Game()
        input.forEach {
            val signs = it.split(" ")
            game.playRound(valueToSignP1[signs[0]], getValueToPlay(valueToSignP1[signs[0]], signs[1]))
        }

        return game.scorePlayer2
    }
    
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}

class Game {
    private val pointPerSign: Map<Sign, Int> = mapOf(Sign.ROCK to 1, Sign.PAPER to 2, Sign.SCISSORS to 3)

    private var comparatorWheel = listOf(Sign.ROCK, Sign.PAPER, Sign.SCISSORS)
    private var scorePlayer1 = 0
    var scorePlayer2 = 0

    fun playRound(signPlayer1: Sign?, signPlayer2: Sign?) {
        scorePlayer1 += pointPerSign[signPlayer1]?: 0
        scorePlayer2 += pointPerSign[signPlayer2]?: 0

        // rotate the wheel until the player1 sign is center
        Collections.rotate(comparatorWheel, 1-comparatorWheel.indexOf(signPlayer1))
        when (1.compareTo(comparatorWheel.indexOf(signPlayer2))) {
            -1 -> scorePlayer2 += 6
            0 -> {
                scorePlayer1 += 3
                scorePlayer2 += 3
            }
            1 -> scorePlayer1 += 6
        }
    }
}


