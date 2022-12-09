package aoc2022

import aoc2022.day09.RopeBridge
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day09Spec : DescribeSpec({

    val testInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input09")

    describe("Part 1 - Visited positions by rope tail") {
        it("test input") {
            RopeBridge.visitedPositionsByTail(testInput.asList()) shouldBe 13
        }

        it("actual input") {
            RopeBridge.visitedPositionsByTail(actualInput.asList()) shouldBe 6081
        }
    }

    describe("Part 2 - Visited positions by end of rope tail of length 9") {
        it("test input") {
            RopeBridge.visitedPositionsByEndOfTailOfLengthNine(testInput.asList()) shouldBe 1

            val largerTest = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20
            """.trimIndent().asInput()
            RopeBridge.visitedPositionsByEndOfTailOfLengthNine(largerTest.asList()) shouldBe 36
        }

        it("actual input") {
            RopeBridge.visitedPositionsByEndOfTailOfLengthNine(actualInput.asList()) shouldBe 2487
        }
    }
})

/*
[Point(x=0, y=0)
 Point(x=0, y=0)
 Point(x=1, y=0)
 Point(x=2, y=0)
 Point(x=3, y=0)
 Point(x=3, y=0)
 Point(x=4, y=1)
 Point(x=4, y=2)
 Point(x=4, y=3)
 Point(x=4, y=3)
 Point(x=3, y=4)
 Point(x=2, y=4)
 Point(x=2, y=4)
 Point(x=2, y=4)
 Point(x=2, y=4)
 Point(x=3, y=4)
 Point(x=4, y=4)
 Point(x=5, y=3)
 Point(x=5, y=3)
 Point(x=4, y=3)
 Point(x=3, y=3)
 Point(x=2, y=3)
 Point(x=1, y=3)
 Point(x=1, y=3)
 Point(x=1, y=3)]



 */
