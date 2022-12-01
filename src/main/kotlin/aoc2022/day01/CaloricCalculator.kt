package aoc2022.day01

object CaloricCalculator {

    fun maxCalories(input: String): Int =
        caloriesPerElf(input).max()

    fun topThreeTotal(input: String): Int =
        caloriesPerElf(input).sortedDescending().take(3).sum()

    private fun caloriesPerElf(input: String): List<Int> {
        return input.split("\n\n")
            .map { it.split("\n").sumOf { s -> s.toInt() } }
    }
}