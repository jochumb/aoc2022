package aoc2022

import aoc2022.day06.TuningTrouble
import aoc2022.utils.Input
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day06Spec : DescribeSpec({

    val actualInput = Input.fromResource("input06")

    describe("Part 1 - start-of-packet marker") {
        it("test input") {
            TuningTrouble.startOfPacketMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 7
            TuningTrouble.startOfPacketMarker("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 5
            TuningTrouble.startOfPacketMarker("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 6
            TuningTrouble.startOfPacketMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 10
            TuningTrouble.startOfPacketMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 11
        }

        it("actual input") {
            TuningTrouble.startOfPacketMarker(actualInput.asString()) shouldBe 1640
        }
    }

    describe("Part 2 - start-of-message marker") {
        it("test input") {
            TuningTrouble.startOfMessageMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 19
            TuningTrouble.startOfMessageMarker("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 23
            TuningTrouble.startOfMessageMarker("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 23
            TuningTrouble.startOfMessageMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 29
            TuningTrouble.startOfMessageMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 26
        }

        it("actual input") {
            TuningTrouble.startOfMessageMarker(actualInput.asString()) shouldBe 3613
        }
    }
})