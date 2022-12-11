package aoc2022.day11

object MonkeyInTheMiddle {

    fun twentyRounds(input: String): Long {
        val monkeys = parseMonkeys(input)
        return doNRounds(20, monkeys) { it / 3 }.multiplyTopTwo()
    }

    fun tenThousandRounds(input: String): Long {
        val monkeys = parseMonkeys(input)
        return doNRounds(10_000, monkeys) {
            it % monkeys.map(Monkey::test).leastCommonMultiple()
        }.multiplyTopTwo()
    }

    private fun doNRounds(n: Int, monkeys: List<Monkey>, reduceWorryLevel: (Long) -> Long): List<Int> =
        (0 until n).fold(Pair(monkeys, List(monkeys.count()) { 0 })) { acc, _ ->
            doRound(acc.first, acc.second, reduceWorryLevel)
        }.second

    private tailrec fun doRound(
        monkeys: List<Monkey>,
        itemsProcessed: List<Int>,
        reduceWorryLevel: (Long) -> Long,
        monkeyId: Int = 0
    ): Pair<List<Monkey>, List<Int>> =
        when {
            monkeyId == monkeys.size          -> Pair(monkeys, itemsProcessed)
            monkeys[monkeyId].items.isEmpty() -> doRound(monkeys, itemsProcessed, reduceWorryLevel, monkeyId + 1)
            else                              -> doRound(
                doTurn(monkeyId, monkeys, reduceWorryLevel),
                itemsProcessed.increment(monkeyId),
                reduceWorryLevel,
                monkeyId
            )
        }

    private fun doTurn(monkeyId: Int, monkeys: List<Monkey>, reduceWorryLevel: (Long) -> Long): List<Monkey> {
        val monkey = monkeys[monkeyId]
        val level: Long = reduceWorryLevel(monkey.operation(monkey.items.first()))
        val nextId = when (level % monkey.test.divisibleBy == 0L) {
            true  -> monkey.test.ifTrue
            false -> monkey.test.ifFalse
        }
        return monkeys.mapIndexed { id, it ->
            when (id) {
                monkeyId -> it.copy(items = it.items.drop(1))
                nextId   -> it.copy(items = it.items + level)
                else     -> it
            }
        }
    }

    private fun parseMonkeys(input: String): List<Monkey> =
        input.split("\n\n").map { parseMonkey(it) }

    private fun parseMonkey(input: String): Monkey {
        val lines = input.split("\n")
        val items = lines[1].substringAfter(":").split(",").map { it.trim().toLong() }
        val operation = parseOperation(lines[2].substringAfter("=").trim())
        val test = Test(
            divisibleBy = lines[3].substringAfterLast(" ").toInt(),
            ifTrue = lines[4].substringAfterLast(" ").toInt(),
            ifFalse = lines[5].substringAfterLast(" ").toInt()
        )
        return Monkey(items, operation, test)
    }

    private fun parseOperation(input: String): (Long) -> Long {
        val (op, other) = Regex("old ([*+]) (.*)").find(input)!!.destructured
        return when {
            op == "*" && other == "old" -> { x: Long -> x * x }
            op == "*"                   -> { x: Long -> x * other.toInt() }
            op == "+" && other == "old" -> { x: Long -> x + x }
            op == "+"                   -> { x: Long -> x + other.toInt() }
            else                        -> throw IllegalArgumentException()
        }
    }

    private data class Monkey(val items: List<Long>, val operation: (Long) -> Long, val test: Test)
    private data class Test(val divisibleBy: Int, val ifTrue: Int, val ifFalse: Int)

    private fun List<Test>.leastCommonMultiple(): Int =
        map { it.divisibleBy }.reduce { a, b ->
            val max = if (a > b) a else b
            generateSequence(max) { it + max }.first { it % a == 0 && it % b == 0 }
        }

    private fun List<Int>.increment(index: Int): List<Int> = mapIndexed { i, n ->
        when (i) {
            index -> n + 1
            else  -> n
        }
    }

    private fun List<Int>.multiplyTopTwo(): Long =
        sortedDescending().take(2).fold(1L) { a, b -> a * b }
}