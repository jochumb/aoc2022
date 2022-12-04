package aoc2022.day04

object CampCleanup {

    fun fullOverlap(input: List<String>): Int =
        input.map(::toRangePair).count(::hasFullOverlap)

    fun partialOverlap(input: List<String>): Int =
        input.map(::toRangePair).count(::hasPartialOverlap)

    private fun toRangePair(assignmentPair: String): Pair<IntRange, IntRange> {
        val digits = Regex("\\d+").findAll(assignmentPair).toList()
        val (s1, e1, s2, e2) = digits.map { it.value }.map { it.toInt() }
        return s1..e1 to s2..e2
    }

    private fun hasFullOverlap(rangePair: Pair<IntRange, IntRange>): Boolean =
        rangePair.intersection().count() == rangePair.minOf { it.count() }

    private fun hasPartialOverlap(rangePair: Pair<IntRange, IntRange>): Boolean =
        rangePair.intersection().isNotEmpty()

}

private fun <T> Pair<Iterable<T>, Iterable<T>>.intersection(): Set<T> =
    this.first.intersect(this.second.toSet())

private inline fun <T, R : Comparable<R>> Pair<T, T>.minOf(selector: (T) -> R): R =
    this.toList().minOf(selector)