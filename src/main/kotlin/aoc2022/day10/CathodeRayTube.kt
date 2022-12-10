package aoc2022.day10

object CathodeRayTube {

    fun signalStrength(input: List<String>): Int {
        val xRegisterByCycle = doCycles(input)
        return xRegisterByCycle.entries
            .filter { it.key % 20 == 0 }
            .filterNot { it.key % 40 == 0 }
            .sumOf { it.key * it.value }
    }

    fun drawPixels(input: List<String>): List<String> {
        val xRegisterByCycle = doCycles(input)
        return draw(xRegisterByCycle)
    }

    private const val MAX_CYCLES = 240

    private tailrec fun doCycles(
        input: List<String>,
        x: Int = 1,
        cycle: Int = 1,
        registerByCycle: Map<Int, Int> = mapOf()
    ): Map<Int, Int> {
        return when {
            input.isEmpty()     -> registerByCycle
            cycle == MAX_CYCLES -> registerByCycle + Pair(cycle, x)
            else                -> when (val step = input.first()) {
                "noop" -> doCycles(input.drop(1), x, cycle + 1, registerByCycle + Pair(cycle, x))
                else   -> doCycles(
                    input.drop(1),
                    x + step.substringAfter(" ").toInt(),
                    cycle + 2,
                    registerByCycle + Pair(cycle, x) + Pair(cycle + 1, x)
                )
            }
        }
    }

    private fun draw(xs: Map<Int, Int>): List<String> {
        return xs.values.chunked(40).map { row ->
            row.mapIndexed { index, value ->
                when (index + 1) {
                    in value..value + 2 -> "#"
                    else                -> "."
                }
            }.joinToString("")
        }
    }
}