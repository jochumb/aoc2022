package aoc2022.day11

object MonkeyInTheMiddle {

    fun twentyRounds(input: String): Long {
        val monkeys = parseMonkeys(input)
        return doNRounds(20, monkeys) { it / 3 }.multiplyTopTwo()
    }

    fun tenThousandRounds(input: String): Long {
        val monkeys = parseMonkeys(input)
        return doNRounds(10_000, monkeys) { item ->
            item % monkeys.map { it.test.divisibleBy }.product()
        }.multiplyTopTwo()
    }

    private fun doNRounds(n: Int, monkeys: List<Monkey>, reduceWorryLevel: (Long) -> Long): List<Int> =
        (0 until n).fold(Pair(monkeys, List(monkeys.count()) { 0 })) { (monkeys, itemsProcessed), _ ->
            doRound(0, monkeys, itemsProcessed, reduceWorryLevel)
        }.second

    private tailrec fun doRound(
        monkeyId: Int,
        monkeys: List<Monkey>,
        itemsProcessed: List<Int>,
        reduceWorryLevel: (Long) -> Long
    ): Pair<List<Monkey>, List<Int>> =
        when {
            monkeyId == monkeys.size          -> Pair(monkeys, itemsProcessed)
            monkeys[monkeyId].items.isEmpty() -> doRound(monkeyId + 1, monkeys, itemsProcessed, reduceWorryLevel)
            else                              -> doRound(
                monkeyId,
                doTurn(monkeyId, monkeys, reduceWorryLevel),
                itemsProcessed.increment(monkeyId),
                reduceWorryLevel
            )
        }

    private fun doTurn(monkeyId: Int, monkeys: List<Monkey>, reduceWorryLevel: (Long) -> Long): List<Monkey> {
        val monkey = monkeys[monkeyId]
        val item = monkey.items.first().let(monkey.operation).let(reduceWorryLevel)
        val nextId = when (item % monkey.test.divisibleBy == 0L) {
            true  -> monkey.test.ifTrue
            false -> monkey.test.ifFalse
        }
        return monkeys.mapIndexed { id, it ->
            when (id) {
                monkeyId -> it.copy(items = it.items.drop(1))
                nextId   -> it.copy(items = it.items + item)
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

    private fun List<Int>.increment(index: Int): List<Int> =
        take(index) + get(index).inc() + drop(index + 1)

    private fun List<Int>.product(): Int = reduce(Int::times)

    private fun List<Int>.multiplyTopTwo(): Long =
        sortedDescending().take(2).fold(1L) { a, b -> a * b }
}