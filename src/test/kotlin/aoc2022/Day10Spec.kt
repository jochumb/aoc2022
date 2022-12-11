package aoc2022

import aoc2022.day10.CathodeRayTube
import aoc2022.utils.Input
import com.github.ajalt.colormath.model.SRGB
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextStyle
import com.github.ajalt.mordant.terminal.Terminal
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
            val pixels = CathodeRayTube.drawPixels(actualInput.asList())
            prettyPrint(pixels)
            pixels shouldBe listOf(
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

private fun prettyPrint(lines: List<String>) {
    val brightOrange = TextStyle(SRGB(1.0F, 0.65F, 0.0F, 1.0F))
    val colors =
        listOf(brightRed, brightOrange, brightYellow, brightGreen, brightCyan, brightBlue, brightMagenta, brightWhite)
    val t = Terminal()
    lines.forEach { line ->
        print((black on black)(" "))
        line.toList().forEachIndexed { index, char ->
            val color = colors[index / 5]
            when (char) {
                '#' -> t.print((color on color)("\u2588"))
                '.' -> t.print((black on black)(" "))
            }
        }
        t.println()
    }
}
