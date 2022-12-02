package aoc2022

import aoc2022.day02.RockPaperScissors
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day02Spec : DescribeSpec({

    val testInput = """
        A Y
        B X
        C Z
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input02")

    describe("Part 1 - calculate score naively") {
        it("test input") {
            RockPaperScissors.Naive.calculateScore(testInput.asList()) shouldBe 15
        }

        it("actual input") {
            RockPaperScissors.Naive.calculateScore(actualInput.asList()) shouldBe 14163
        }
    }

    describe("Part 2 - calculate score properly") {
        it("test input") {
            RockPaperScissors.Proper.calculateScore(testInput.asList()) shouldBe 12
        }

        it("actual input") {
            RockPaperScissors.Proper.calculateScore(actualInput.asList()) shouldBe 12091
        }
    }
})