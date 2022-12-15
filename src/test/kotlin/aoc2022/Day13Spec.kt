package aoc2022

import aoc2022.day13.DistressSignal
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day13Spec : DescribeSpec({

    val testInput = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input13")

    describe("Part 1 - Count correctly ordered packet pairs") {
        it("test input") {
            DistressSignal.countCorrectlyOrderedPacketPairs(testInput.asList()) shouldBe 13
        }

        it("actual input") {
            DistressSignal.countCorrectlyOrderedPacketPairs(actualInput.asList()) shouldBe 6568
        }
    }

    describe("Part 2 - Order packets with divider packets") {
        it("test input") {
            DistressSignal.orderPacketsWithDividerPackets(testInput.asList()) shouldBe 140
        }

        it("actual input") {
            DistressSignal.orderPacketsWithDividerPackets(actualInput.asList()) shouldBe 19493
        }
    }
})
