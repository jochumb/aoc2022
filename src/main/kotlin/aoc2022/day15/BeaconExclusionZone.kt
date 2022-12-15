package aoc2022.day15

import kotlin.math.abs

object BeaconExclusionZone {

    fun coveredPointsInLine(input: List<String>, y: Int): Int {
        val zones = input.map { toZone(it) }
        val xAxis = xAxisBoundaries(zones).let { it.first..it.second }
        return xAxis.asSequence()
            .map { x -> Point(x, y) }
            .filter { it.isInAZone(zones) }
            .filterNot { it.isABeacon(zones) }
            .count()
    }

    fun findUncoveredBeacon(input: List<String>, maxGridSize: Int): Long {
        val zones = input.map { toZone(it) }
        val beacon = zones.asSequence().flatMap { it.justOutside(maxGridSize) }.first { !it.isInAZone(zones) }
        return beacon.x * 4000000L + beacon.y
    }

    private data class Point(val x: Int, val y: Int) {
        fun manhattanDistance(other: Point): Int =
            abs(x - other.x) + abs(y - other.y)

        fun isInAZone(zones: List<Zone>): Boolean =
            zones.any { zone -> zone.isInZone(this) }

        fun isABeacon(zones: List<Zone>): Boolean =
            zones.any { zone -> zone.beacon == this }

        fun isInGrid(maxGridSize: Int) =
            x in 0..maxGridSize && y in 0..maxGridSize
    }

    private data class Zone(val sensor: Point, val beacon: Point) {
        val radius = sensor.manhattanDistance(beacon)

        fun isInZone(point: Point): Boolean =
            sensor.manhattanDistance(point) <= radius

        fun justOutside(maxGridSize: Int): List<Point> =
            boundaryPointsFrom(sensor.x + radius + 1, maxGridSize) +
                    boundaryPointsFrom(sensor.x - radius - 1, maxGridSize)

        private fun boundaryPointsFrom(start: Int, maxGridSize: Int): List<Point> =
            (start until sensor.x).withIndex().flatMap { (dy, x) ->
                listOf(Point(x, sensor.y - dy), Point(x, sensor.y + dy)).filter { it.isInGrid(maxGridSize) }
            }

    }

    private fun toZone(input: String): Zone {
        val digits = Regex("([-\\d]+)").findAll(input).map { it.value.toInt() }.toList()
        val (sx, sy, bx, by) = digits
        return Zone(Point(sx, sy), Point(bx, by))
    }

    private fun xAxisBoundaries(zones: List<Zone>): Pair<Int, Int> {
        val allXs = zones.flatMap {
            listOf(
                it.sensor.x - it.radius,
                it.sensor.x + it.radius,
                it.beacon.x
            )
        }
        return allXs.min() to allXs.max()
    }

}