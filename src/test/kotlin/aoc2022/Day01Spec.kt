package aoc2022

import aoc2022.day01.CaloricCalculator
import aoc2022.utils.Input
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day01Spec : StringSpec({

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

    "Part 1 - max calories test" {
        CaloricCalculator.maxCalories(testInput) shouldBe 24000
    }

    "Part 1 - from input" {
        val input = Input.fromResource("input01").asString()
        CaloricCalculator.maxCalories(input) shouldBe 74711
    }


    "Part 2 - top 3 total calories test" {
        CaloricCalculator.topThreeTotal(testInput) shouldBe 45000
    }

    "Part 2 - from input" {
        val input = Input.fromResource("input01").asString()
        CaloricCalculator.topThreeTotal(input) shouldBe 209481
    }
})