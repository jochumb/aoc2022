package aoc2022.day14

import kotlin.math.max
import kotlin.math.min

object RegolithReservoir {

    fun dropSandUntilOverflow(input: List<String>): Int {
        val rocks = buildRockStructure(input)
        return dropSand(rocks).count()
    }

    fun dropSandUntilSourceIsBlocked(input: List<String>): Int {
        val rocks = buildRockStructure(input)
        val pyramid = sandPyramid(rocks.maxOf { it.y } + 1)
        val blocked = findBlocked(pyramid, rocks)
        return (pyramid - blocked.toSet()).count()
    }

    private tailrec fun dropSand(
        rocks: List<Point>,
        current: Point = Point.SOURCE,
        sand: List<Point> = emptyList(),
        abyss: Int = rocks.maxOf { it.y }
    ): List<Point> {
        return when {
            current.intoTheAbyss(abyss)        -> sand
            current.canDropDown(rocks + sand)  -> dropSand(rocks, current.dropDown(), sand, abyss)
            current.canDropLeft(rocks + sand)  -> dropSand(rocks, current.dropLeft(), sand, abyss)
            current.canDropRight(rocks + sand) -> dropSand(rocks, current.dropRight(), sand, abyss)
            else                               -> dropSand(rocks, Point.SOURCE, sand + current, abyss)
        }
    }

    private fun sandPyramid(floor: Int): List<Point> =
        (0..floor).flatMap { y ->
            (500 - y..500 + y).map { x -> Point(x, y) }
        }

    private tailrec fun findBlocked(pyramid: List<Point>, blocked: List<Point>): List<Point> {
        if (pyramid.isEmpty()) return blocked

        val point = pyramid.first()
        return if (blocked.containsAll(listOf(point, point.left(), point.right()))) {
            findBlocked(pyramid.drop(1), blocked + point.down())
        } else {
            findBlocked(pyramid.drop(1), blocked)
        }
    }

    private fun buildRockStructure(input: List<String>): List<Point> = input.flatMap { buildRockPath(it) }

    private fun buildRockPath(input: String): List<Point> =
        input.split(" -> ").map {
            it.split(",").map(String::toInt).let { (x, y) -> Point(x, y) }
        }.windowed(2)
            .flatMap { (start, end) -> buildRockLedge(start, end) }
            .distinct()

    private fun buildRockLedge(start: Point, end: Point): List<Point> =
        (min(start.x, end.x)..max(start.x, end.x)).flatMap { x ->
            (min(start.y, end.y)..max(start.y, end.y)).map { y ->
                Point(x, y)
            }
        }

    private data class Point(val x: Int, val y: Int) {
        companion object {
            val SOURCE = Point(500, 0)
        }

        fun intoTheAbyss(abyss: Int) = abyss == y

        fun canDropDown(taken: List<Point>) = dropDown() !in taken
        fun dropDown() = copy(y = y + 1)

        fun canDropLeft(taken: List<Point>) = dropLeft() !in taken
        fun dropLeft() = copy(x = x - 1, y = y + 1)

        fun canDropRight(taken: List<Point>) = dropRight() !in taken
        fun dropRight() = copy(x = x + 1, y = y + 1)

        fun left() = copy(x = x - 1)
        fun right() = copy(x = x + 1)
        fun down() = copy(y = y + 1)
    }
}