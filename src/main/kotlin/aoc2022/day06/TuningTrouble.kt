package aoc2022.day06

object TuningTrouble {

    fun startOfPacketMarker(input: String): Int =
        markerIndexAfterDistinctCharacters(input.toList(), 4)

    fun startOfMessageMarker(input: String): Int =
        markerIndexAfterDistinctCharacters(input.toList(), 14)

    private fun markerIndexAfterDistinctCharacters(chars: List<Char>, count: Int): Int =
        chars.asSequence()
            .windowed(count, 1)
            .withIndex()
            .filter { it.value.distinct().count() == count }
            .first().index + count
}