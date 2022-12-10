package aoc2022

import aoc2022.day10.CathodeRayTube
import aoc2022.utils.Input
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day10Spec : DescribeSpec({

    val testInput = Input.fromResource("input10-test")

    val actualInput = Input.fromResource("input10")

    describe("Part 1 - Signal strength") {
        it("test input") {
            CathodeRayTube.signalStrength(testInput.asList()) shouldBe 13140
        }

        it("actual input") {
            CathodeRayTube.signalStrength(actualInput.asList()) shouldBe 13680
        }
    }

    describe("Part 2 - Draw pixels") {
        it("test input") {
            CathodeRayTube.drawPixels(testInput.asList()) shouldBe listOf(
                "##..##..##..##..##..##..##..##..##..##..",
                "###...###...###...###...###...###...###.",
                "####....####....####....####....####....",
                "#####.....#####.....#####.....#####.....",
                "######......######......######......####",
                "#######.......#######.......#######....."
            )
        }

        it("actual input") {
            CathodeRayTube.drawPixels(actualInput.asList()) shouldBe listOf(
                "###..####..##..###..#..#.###..####.###..",
                "#..#....#.#..#.#..#.#.#..#..#.#....#..#.",
                "#..#...#..#....#..#.##...#..#.###..###..",
                "###...#...#.##.###..#.#..###..#....#..#.",
                "#....#....#..#.#....#.#..#....#....#..#.",
                "#....####..###.#....#..#.#....####.###.."
            )
        }
    }
})