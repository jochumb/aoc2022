package aoc2022

import aoc2022.day04.CampCleanup
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day04Spec : DescribeSpec({
    val testInput = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input04")

    describe("Part 1 - full overlap") {
        it("test input") {
            CampCleanup.fullOverlap(testInput.asList()) shouldBe 2
        }

        it("actual input") {
            CampCleanup.fullOverlap(actualInput.asList()) shouldBe 582
        }
    }

    describe("Part 2 - partial overlap") {
        it("test input") {
            CampCleanup.partialOverlap(testInput.asList()) shouldBe 4
        }

        it("actual input") {
            CampCleanup.partialOverlap(actualInput.asList()) shouldBe 893
        }
    }
})