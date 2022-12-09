package aoc2022.day09

import kotlin.math.abs
import kotlin.math.nextTowards
import kotlin.math.roundToInt

object RopeBridge {

    fun visitedPositionsByTail(input: List<String>): Int {
        val moves = input.flatMap { Move.fromString(it) }
        return followHead(moves).count()
    }

    fun visitedPositionsByEndOfTailOfLengthNine(input: List<String>): Int {
        val moves = input.flatMap { Move.fromString(it) }
        return followHead(moves, tail = Tail.withKnots(9)).count()
    }

    private tailrec fun followHead(
        moves: List<Move>,
        head: Point = Point.START,
        tail: Tail = Tail.ONE_LENGTH,
        visited: Set<Point> = setOf(Point.START)
    ): Set<Point> {
        if (moves.isEmpty()) return visited

        val movedHead = head.move(moves.first())
        val movedTail = tail.follow(movedHead)
        return followHead(moves.drop(1), movedHead, movedTail, visited + movedTail.last)
    }

    private enum class Move {
        RIGHT, LEFT, UP, DOWN;

        companion object {
            fun fromString(input: String): List<Move> {
                val count = input.substringAfter(" ").toInt()
                return when (input.substringBefore(" ")) {
                    "R"  -> List(count) { RIGHT }
                    "L"  -> List(count) { LEFT }
                    "U"  -> List(count) { UP }
                    "D"  -> List(count) { DOWN }
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    private data class Point(val x: Int, val y: Int) {
        fun move(move: Move): Point =
            when (move) {
                Move.RIGHT -> copy(x = x + 1)
                Move.LEFT  -> copy(x = x - 1)
                Move.UP    -> copy(y = y + 1)
                Move.DOWN  -> copy(y = y - 1)
            }

        fun isConnected(other: Point): Boolean =
            abs(y - other.y) <= 1 && abs(x - other.x) <= 1


        fun follow(other: Point): Point {
            val otherX: Double = other.x.toDouble()
            val otherY: Double = other.y.toDouble()
            return Point(
                x = ((x + otherX) / 2).nextTowards(otherX).roundToInt(),
                y = ((y + otherY) / 2).nextTowards(otherY).roundToInt()
            )
        }

        companion object {
            val START = Point(0, 0)
        }
    }

    private data class Tail(val knot: Point, val next: Tail? = null) {
        val last: Point = when (next) {
            null -> knot
            else -> next.last
        }

        fun follow(other: Point): Tail {
            if (other.isConnected(knot)) return this

            val nextPoint = knot.follow(other)
            return when (next) {
                null -> Tail(knot = nextPoint)
                else -> Tail(knot = nextPoint, next = next.follow(nextPoint))
            }
        }

        companion object {
            val ONE_LENGTH = withKnots(1)

            fun withKnots(count: Int): Tail =
                (1 until count).fold(Tail(Point.START)) { acc, _ -> Tail(knot = Point.START, next = acc) }
        }
    }
}