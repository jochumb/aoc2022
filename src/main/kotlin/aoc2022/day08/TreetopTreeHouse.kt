package aoc2022.day08

object TreetopTreeHouse {

    fun visibleTreeCount(input: List<String>): Int {
        val matrix = createTreeMatrix(input)
        val visible = treesVisibleFromSides(matrix)
        return visible.count()
    }

    fun maxScenicScore(input: List<String>): Int {
        val matrix = createTreeMatrix(input)
        val scenicScores = calculateScenicScores(matrix)
        return scenicScores.flatten().max()
    }

    private fun createTreeMatrix(input: List<String>): Matrix<Tree> =
        input.mapIndexed { y, sizes -> sizes.mapIndexed { x, size -> Tree(Point(x, y), size.digitToInt()) } }

    private fun treesVisibleFromSides(matrix: Matrix<Tree>): Set<Tree> =
        treesVisibleFromOneSide(matrix) +
                treesVisibleFromOneSide(matrix.reversed()) +
                treesVisibleFromOneSide(matrix.transpose()) +
                treesVisibleFromOneSide(matrix.transpose().reversed())

    private fun treesVisibleFromOneSide(matrix: Matrix<Tree>): Set<Tree> {
        data class Acc(val highestTree: Int = -1, val visibleTrees: Set<Tree> = emptySet())

        return matrix.flatMap { row ->
            row.fold(Acc()) { acc, tree ->
                if (tree.size > acc.highestTree) {
                    Acc(tree.size, acc.visibleTrees + tree)
                } else acc
            }.visibleTrees
        }.toSet()
    }

    private fun calculateScenicScores(matrix: Matrix<Tree>): Matrix<Int> =
        matrix.map { row -> row.map { calculateScenicScore(it, matrix) } }

    private fun calculateScenicScore(tree: Tree, matrix: Matrix<Tree>): Int =
        visibility(tree, matrix) { it.copy(y = it.y - 1) } *
                visibility(tree, matrix) { it.copy(y = it.y + 1) } *
                visibility(tree, matrix) { it.copy(x = it.x - 1) } *
                visibility(tree, matrix) { it.copy(x = it.x + 1) }

    private tailrec fun visibility(
        tree: Tree,
        matrix: Matrix<Tree>,
        current: Point = tree.point,
        count: Int = 0,
        toNextPoint: (Point) -> Point
    ): Int {
        val next = toNextPoint(current)
        return when {
            !matrix.isInBounds(next.x, next.y)       -> count
            matrix[next.y][next.x].size >= tree.size -> count + 1
            else                                     -> visibility(tree, matrix, next, count + 1, toNextPoint)
        }
    }

    private data class Tree(val point: Point, val size: Int)
    private data class Point(val x: Int, val y: Int)
}

typealias Matrix<T> = List<List<T>>

private fun <T> Matrix<T>.transpose(): Matrix<T> {
    return (0 until this.first().count()).map { x ->
        (this.indices).map { y ->
            this[y][x]
        }
    }
}

private fun <T> Matrix<T>.reversed(): Matrix<T> {
    return this.map { it.reversed() }
}

private fun <T> Matrix<T>.isInBounds(x: Int, y: Int): Boolean =
    y >= 0 && x >= 0 && y < this.count() && x < this.first().count()