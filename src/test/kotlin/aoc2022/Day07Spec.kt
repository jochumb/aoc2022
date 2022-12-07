package aoc2022

import aoc2022.day08.NoSpaceLeftOnDevice
import aoc2022.utils.Input
import aoc2022.utils.asInput
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day07Spec : DescribeSpec({

    val testInput = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().asInput()

    val actualInput = Input.fromResource("input07")

    describe("Part 1 - Sum dirs with sizes of at most 100000") {
        it("test input") {
            NoSpaceLeftOnDevice.sumDirsWithSizesOfAtMost100000(testInput.asList()) shouldBe 95_437
        }

        it("actual input") {
            NoSpaceLeftOnDevice.sumDirsWithSizesOfAtMost100000(actualInput.asList()) shouldBe 1_307_902
        }
    }

    describe("Part 2 - Size of smallest dir to free enough space") {
        it("test input") {
            NoSpaceLeftOnDevice.sizeOfSmallestDirToFreeEnoughSpace(testInput.asList()) shouldBe 24_933_642
        }

        it("actual input") {
            NoSpaceLeftOnDevice.sizeOfSmallestDirToFreeEnoughSpace(actualInput.asList()) shouldBe 7_068_748
        }
    }
})