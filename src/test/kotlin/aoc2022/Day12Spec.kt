package aoc2022

import aoc2022.day12.HillClimbingAlgorithm
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day12Spec : DescribeSpec({

    val testInput = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input12")

    describe("Part 1 - Shortest path from start") {
        it("test input") {
            HillClimbingAlgorithm.shortestPathFromStart(testInput.asList()) shouldBe 31
        }

        it("actual input") {
            HillClimbingAlgorithm.shortestPathFromStart(actualInput.asList()) shouldBe 528
        }
    }

    describe("Part 2 - Shortest path from lowest level") {
        it("test input") {
            HillClimbingAlgorithm.shortestPathFromLowestLevel(testInput.asList()) shouldBe 29
        }

        it("actual input") {
            HillClimbingAlgorithm.shortestPathFromLowestLevel(actualInput.asList()) shouldBe 522
        }
    }
})
