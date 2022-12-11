package aoc2022.day01

object CalorieCounting {

    fun maxCalories(input: String): Int =
        caloriesPerElf(input).max()

    fun topThreeTotal(input: String): Int =
        caloriesPerElf(input).sortedDescending().take(3).sum()

    private fun caloriesPerElf(input: String): List<Int> =
        input.split("\n\n").map { it.split("\n").sumOf(String::toInt) }
}