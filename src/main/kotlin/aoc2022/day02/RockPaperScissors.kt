package aoc2022.day02

object RockPaperScissors {

    object Naive {
        fun calculateScore(input: List<String>): Int =
            input.map(::toRound).sumOf(Round::score)

        private fun toRound(input: String): Round =
            Round.byShapes(Shape.from(input.first()), Shape.from(input.last()))
    }

    object Proper {
        fun calculateScore(input: List<String>): Int =
            input.map(::toRound).sumOf(Round::score)

        private fun toRound(input: String): Round =
            Round.byOutcome(Shape.from(input.first()), Outcome.from(input.last()))
    }
}

data class Round(val shape: Shape, val outcome: Outcome) {

    val score: Int = shape.points + outcome.points

    companion object {
        fun byShapes(opponent: Shape, me: Shape): Round =
            when (me) {
                opponent -> Round(me, Outcome.DRAW)
                opponent.beats() -> Round(me, Outcome.LOSE)
                else -> Round(me, Outcome.WIN)
            }

        fun byOutcome(opponent: Shape, outcome: Outcome): Round =
            when (outcome) {
                Outcome.LOSE -> Round(opponent.beats(), outcome)
                Outcome.DRAW -> Round(opponent, outcome)
                Outcome.WIN -> Round(opponent.beats().beats(), outcome)
            }
    }
}

enum class Shape(val points: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    companion object {
        fun from(input: Char): Shape =
            when (input) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw IllegalArgumentException()
            }
    }
}

fun Shape.beats(): Shape =
    when (this) {
        Shape.ROCK -> Shape.SCISSORS
        Shape.PAPER -> Shape.ROCK
        Shape.SCISSORS -> Shape.PAPER
    }


enum class Outcome(val points: Int) {
    LOSE(0), DRAW(3), WIN(6);

    companion object {
        fun from(input: Char): Outcome =
            when (input) {
                'X' -> LOSE
                'Y' -> DRAW
                'Z' -> WIN
                else -> throw IllegalArgumentException()
            }
    }
}
