package aoc2022.day13

object DistressSignal {
    fun countCorrectlyOrderedPacketPairs(input: List<String>): Int =
        createPacketSequence(input).chunked(2).withIndex()
            .filter { (_, items) -> items[0] < items[1] }
            .map { it.index + 1 }.sum()

    fun orderPacketsWithDividerPackets(input: List<String>): Int {
        val firstDividerPacket = ListValue(ListValue(IntValue(2)))
        val secondDividerPacket = ListValue(ListValue(IntValue(6)))

        val sortedPackets = (sequenceOf(firstDividerPacket, secondDividerPacket) + createPacketSequence(input)).sorted()
        return (sortedPackets.indexOf(firstDividerPacket) + 1) * (sortedPackets.indexOf(secondDividerPacket) + 1)
    }

    private fun createPacketSequence(input: List<String>) =
        input.asSequence().filter { it.isNotBlank() }.map { createListValue(it.toList()) }

    private sealed interface ListOrInt : Comparable<ListOrInt> {
        override fun compareTo(other: ListOrInt): Int
    }

    private data class IntValue(val value: Int) : ListOrInt {
        fun asListValue(): ListValue = ListValue(this)

        override fun compareTo(other: ListOrInt): Int =
            when (other) {
                is ListValue -> asListValue().compareTo(other)
                is IntValue  -> this.value - other.value
            }
    }

    private data class ListValue(val value: List<ListOrInt>) : ListOrInt {
        companion object {
            operator fun invoke(vararg values: ListOrInt) = ListValue(values.asList())
        }

        override fun compareTo(other: ListOrInt): Int {
            return when (other) {
                is IntValue  -> compareTo(other.asListValue())
                is ListValue -> {
                    if (value.isEmpty()) return -1
                    if (other.value.isEmpty()) return 1

                    val comparedItems = value.zip(other.value).map { (item, otherItem) -> item.compareTo(otherItem) }
                    if (comparedItems.all { it == 0 }) return value.count() - other.value.count()
                    if (comparedItems.first { it != 0 } > 0) return 1
                    return -1
                }
            }
        }
    }

    private fun createListValue(section: List<Char>) = ListValue(fillListValue(section.drop(1).dropLast(1)))

    private fun fillListValue(section: List<Char>): List<ListOrInt> =
        when (section.firstOrNull()) {
            null        -> emptyList()
            '['         -> nextListValue(section)
            in '0'..'9' -> nextIntValue(section)
            else        -> fillListValue(section.drop(1))
        }

    private fun nextListValue(section: List<Char>): List<ListOrInt> {
        val listEndIndex = indexOnBalancedOpenAndClose(section.drop(1))
        return listOf(createListValue(section.subList(0, listEndIndex + 1))) +
                fillListValue(section.subList(listEndIndex + 1, section.lastIndex + 1))
    }

    private fun indexOnBalancedOpenAndClose(chars: List<Char>, tally: Int = 1, index: Int = 1): Int =
        when (chars.first()) {
            '['  -> indexOnBalancedOpenAndClose(chars.drop(1), tally + 1, index + 1)
            ']'  -> if (tally == 1) index else indexOnBalancedOpenAndClose(chars.drop(1), tally - 1, index + 1)
            else -> indexOnBalancedOpenAndClose(chars.drop(1), tally, index + 1)
        }

    private fun nextIntValue(section: List<Char>): List<ListOrInt> {
        val numbers = section.takeWhile { it.isDigit() }
        return listOf(IntValue(numbers.joinToString("").toInt())) + fillListValue(section.drop(numbers.count()))
    }
}

