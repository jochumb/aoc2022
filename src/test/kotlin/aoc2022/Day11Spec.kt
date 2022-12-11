package aoc2022

import aoc2022.day11.MonkeyInTheMiddle
import aoc2022.utils.Input
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day11Spec : DescribeSpec({

    val testInput = Input.fromResource("input11-test")

    val actualInput = Input.fromResource("input11")

    describe("Part 1 - 20 rounds") {
        it("test input") {
            MonkeyInTheMiddle.twentyRounds(testInput.asString()) shouldBe 10605L
        }

        it("actual input") {
            MonkeyInTheMiddle.twentyRounds(actualInput.asString()) shouldBe 120056L
        }
    }

    describe("Part 2 - 10000 rounds") {
        it("test input") {
            MonkeyInTheMiddle.tenThousandRounds(testInput.asString()) shouldBe 2713310158L
        }

        it("actual input") {
            MonkeyInTheMiddle.tenThousandRounds(actualInput.asString()) shouldBe 21816744824L
        }
    }
})