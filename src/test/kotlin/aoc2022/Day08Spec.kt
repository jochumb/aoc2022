package aoc2022

import aoc2022.day08.TreetopTreeHouse
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day08Spec : DescribeSpec({

    val testInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input08")

    describe("Part 1 - Visible tree count") {
        it("test input") {
            TreetopTreeHouse.visibleTreeCount(testInput.asList()) shouldBe 21
        }

        it("actual input") {
            TreetopTreeHouse.visibleTreeCount(actualInput.asList()) shouldBe 1546
        }
    }

    describe("Part 2 - Max scenic score") {
        it("test input") {
            TreetopTreeHouse.maxScenicScore(testInput.asList()) shouldBe 8
        }

        it("actual input") {
            TreetopTreeHouse.maxScenicScore(actualInput.asList()) shouldBe 519064
        }
    }
})
