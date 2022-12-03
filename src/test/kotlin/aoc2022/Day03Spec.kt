package aoc2022

import aoc2022.day03.RucksackReorganization
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day03Spec : DescribeSpec({

    val testInput = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input03")

    describe("Part 1 - sum priorities") {
        it("test input") {
            RucksackReorganization.prioritySum(testInput.asList()) shouldBe 157
        }

        it("actual input") {
            RucksackReorganization.prioritySum(actualInput.asList()) shouldBe 7811
        }
    }

    describe("Part 2 - sum priorities by groups of three") {
        it("test input") {
            RucksackReorganization.groupPrioritySum(testInput.asList()) shouldBe 70
        }

        it("actual input") {
            RucksackReorganization.groupPrioritySum(actualInput.asList()) shouldBe 2639
        }
    }

})