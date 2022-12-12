package aoc2022.day12

import arrow.fx.coroutines.parTraverse
import kotlin.math.abs
import kotlin.math.max

data class Point(val x: Int, val y: Int)

object HillClimbingAlgorithm {

    fun shortestPathFromStart(input: List<String>): Int {
        val (pointsMap, starts, end) = createPointMap(input)
        val nodes: Map<Point, Node<Point>> =
            pointsMap.map { e -> toNode(e.key, e.value, pointsMap) }.associateBy { it.id }

        return AStar.shortestPath(nodes, starts.first(), end).count() - 1
    }

    suspend fun shortestPathFromLowestLevel(input: List<String>): Int {
        val (pointsMap, starts, end) = createPointMap(input, startToken = 'a')
        val nodes: Map<Point, Node<Point>> =
            pointsMap.map { e -> toNode(e.key, e.value, pointsMap) }.associateBy { it.id }

        return starts.parTraverse { start ->
            AStar.shortestPath(nodes, start, end)
        }.filter { it.count() > 1 }.minOf { it.count() - 1 }
    }

    private fun createPointMap(
        input: List<String>,
        startToken: Char = 'S'
    ): Triple<Map<Point, Int>, Set<Point>, Point> {
        val pointsMap: Map<Point, Pair<Int, Char>> =
            input.mapIndexed { y, xs ->
                xs.mapIndexed { x, char -> createPoint(x, y, char) }
            }.flatten().toMap()

        val starts: Set<Point> = pointsMap.filter { e -> e.value.second == startToken }.keys
        val end: Point = pointsMap.filter { e -> e.value.second == 'E' }.keys.first()

        return Triple(pointsMap.mapValues { it.value.first }, starts, end)
    }

    private fun createPoint(x: Int, y: Int, char: Char): Pair<Point, Pair<Int, Char>> {
        val height = when (char) {
            'S'  -> 1
            'E'  -> 26
            else -> char.code - 'a'.code + 1
        }
        return Pair(Point(x, y), Pair(height, char))
    }

    private fun toNode(point: Point, value: Int, pointsMap: Map<Point, Int>): Node<Point> {
        val neighbourPoints = listOf(
            point.copy(x = point.x + 1), point.copy(x = point.x - 1),
            point.copy(y = point.y + 1), point.copy(y = point.y - 1)
        ).filter { it.x >= 0 && it.y >= 0 }

        val neighbours = neighbourPoints
            .map { Pair(it, pointsMap[it]) }
            .filter { (it.second ?: 0) in 1..value + 1 }
            .map { Edge(to = it.first) }

        return Node(point, neighbours) { other ->
            max(
                abs(point.x - other.id.x) + abs(point.y - other.id.y),
                26 - value
            )
        }
    }
}
