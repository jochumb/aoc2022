package aoc2022

import aoc2022.day14.RegolithReservoir
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day14Spec : DescribeSpec({

    val testInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input14")

    describe("Part 1 - Drop sand until overflow") {
        it("test input") {
            RegolithReservoir.dropSandUntilOverflow(testInput.asList()) shouldBe 24
        }

        it("actual input") {
            RegolithReservoir.dropSandUntilOverflow(actualInput.asList()) shouldBe 1068
        }
    }

    describe("Part 2 - Drop sand until source is blocked") {
        it("test input") {
            RegolithReservoir.dropSandUntilSourceIsBlocked(testInput.asList()) shouldBe 93
        }

        it("actual input") {
            RegolithReservoir.dropSandUntilSourceIsBlocked(actualInput.asList()) shouldBe 27936
        }
    }
})
