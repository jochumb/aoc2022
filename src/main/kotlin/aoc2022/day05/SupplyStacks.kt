package aoc2022.day05

object SupplyStacks {

    fun moveCratesOneByOne(input: List<String>): String {
        val position = parsePosition(input)
        val stacks: List<Stack<Char>> = position.steps.fold(position.stacks, ::executeStepOneByOne)
        return stacks.map { it.first() }.joinToString("")
    }

    fun moveCratesAllAtOnce(input: List<String>): String {
        val position = parsePosition(input)
        val stacks: List<Stack<Char>> = position.steps.fold(position.stacks, ::executeStep)
        return stacks.map { it.first() }.joinToString("")
    }

    private fun executeStepOneByOne(stacks: List<Stack<Char>>, step: Step): List<Stack<Char>> {
        if (step.count == 0) return stacks
        val newStacks = executeStep(stacks, step.copy(count = 1))
        return executeStepOneByOne(newStacks, step.copy(count = step.count - 1))
    }

    private fun executeStep(stacks: List<Stack<Char>>, step: Step): List<Stack<Char>> {
        val crates: List<Char> = stacks[step.from].take(step.count)
        val newFromStack = stacks[step.from].drop(step.count)
        val newToStack = crates + stacks[step.to]

        return stacks.mapIndexed { index, stack ->
            when (index) {
                step.from -> newFromStack
                step.to -> newToStack
                else -> stack
            }
        }
    }

    private fun parsePosition(input: List<String>): Position {
        val splitIndex = input.indexOf("")
        val stacksInput = input.subList(0, splitIndex - 1)
        val stepsInput = input.subList(splitIndex + 1, input.size)
        return Position(parseStacks(stacksInput), parseSteps(stepsInput))
    }

    private fun parseStacks(input: List<String>): List<Stack<Char>> {
        val stripped = input.map { it.toList() }.map { chars ->
            chars.filterIndexed { i, _ -> i % 2 != 0 }.filterIndexed { i, _ -> i % 2 == 0 }
        }

        return IntRange(0, stripped.maxOf { it.count() } - 1).map { i ->
            stripped.map { it.getOrElse(i) { ' ' } }.filter { it != ' ' }
        }
    }

    private fun parseSteps(input: List<String>): List<Step> =
        input.map { Regex("move (\\d+) from (\\d+) to (\\d+)").find(it)!! }.map { matchResult ->
            val (count, from, to) = matchResult.destructured
            Step(count.toInt(), from.toInt() - 1, to.toInt() - 1)
        }

}

data class Position(val stacks: List<Stack<Char>>, val steps: List<Step>)

data class Step(val count: Int, val from: Int, val to: Int)

typealias Stack<T> = List<T>