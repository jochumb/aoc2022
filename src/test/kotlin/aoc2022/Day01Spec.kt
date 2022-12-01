package aoc2022

import aoc2022.day01.CaloricCalculator
import aoc2022.utils.Input
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day01Spec : DescribeSpec({

    val testInput = """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
        """.trimIndent()

    val actualInput = Input.fromResource("input01").asString()

    describe("Part 1 - max calories") {
        it("test input") {
            CaloricCalculator.maxCalories(testInput) shouldBe 24000
        }

        it("actual input") {
            CaloricCalculator.maxCalories(actualInput) shouldBe 74711
        }
    }

    describe("Part 2 - top 3 total calories") {
        it("test input") {
            CaloricCalculator.topThreeTotal(testInput) shouldBe 45000
        }

        it("actual input") {
            CaloricCalculator.topThreeTotal(actualInput) shouldBe 209481
        }
    }
})