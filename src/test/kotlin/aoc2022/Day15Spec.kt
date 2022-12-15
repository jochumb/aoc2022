package aoc2022

import aoc2022.day15.BeaconExclusionZone
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day15Spec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val testInput = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input15")

    describe("Part 1 - Covered points in line") {
        it("test input") {
            BeaconExclusionZone.coveredPointsInLine(testInput.asList(), 10) shouldBe 26
        }

        it("actual input") {
            BeaconExclusionZone.coveredPointsInLine(actualInput.asList(), 2_000_000) shouldBe 5_256_611
        }
    }

    describe("Part 2 - Find uncovered beacon") {
        it("test input") {
            BeaconExclusionZone.findUncoveredBeacon(testInput.asList(), 20) shouldBe 56_000_011L
        }

        it("actual input") {
            BeaconExclusionZone.findUncoveredBeacon(actualInput.asList(), 4_000_000) shouldBe 13_337_919_186_981L
        }
    }
})
