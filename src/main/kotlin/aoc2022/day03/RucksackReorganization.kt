package aoc2022.day03

object RucksackReorganization {

    fun prioritySum(rucksacks: List<String>): Int =
        rucksacks.map(::splitEqual).map(::commonItem).sumOf(::toPriority)

    fun groupPrioritySum(rucksacks: List<String>): Int =
        rucksacks.chunked(3).map(::commonItem).sumOf(::toPriority)

    private fun splitEqual(rucksack: String): List<String> {
        val half: Int = rucksack.length / 2
        return listOf(rucksack.substring(0, half), rucksack.substring(half))
    }

    private fun commonItem(strings: List<String>): Char =
        strings.map(String::toSet).reduce { acc, chars -> chars intersect acc }.first()

    private fun toPriority(item: Char): Int =
        when (item.isLowerCase()) {
            true -> item.code - 96
            false -> item.code - 38
        }
}