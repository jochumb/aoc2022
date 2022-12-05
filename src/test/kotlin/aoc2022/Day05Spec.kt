package aoc2022

import aoc2022.day05.SupplyStacks
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day05Spec : DescribeSpec({
    val testInput = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input05")

    describe("Part 1 - Move crates one at a time") {
        it("test input") {
            SupplyStacks.moveCratesOneByOne(testInput.asList()) shouldBe "CMZ"
        }

        it("actual input") {
            SupplyStacks.moveCratesOneByOne(actualInput.asList()) shouldBe "GFTNRBZPF"
        }
    }

    describe("Part 2 - Move crates all at once") {
        it("test input") {
            SupplyStacks.moveCratesAllAtOnce(testInput.asList()) shouldBe "MCD"
        }

        it("actual input") {
            SupplyStacks.moveCratesAllAtOnce(actualInput.asList()) shouldBe "VRQWPDSGP"
        }
    }
})