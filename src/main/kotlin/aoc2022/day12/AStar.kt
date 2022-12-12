package aoc2022.day12

data class Node<T>(
    val id: T,
    val neighbours: List<Edge<T>>,
    val heuristicFunction: (Node<T>) -> Int
)

data class Edge<T>(val to: T, val cost: Int = 1)

class AStar<T>(private val nodes: Map<T, Node<T>>) {

    private data class State<T>(
        val unprocessed: Set<Pair<Node<T>, Int>>,
        val processed: Set<T> = emptySet(),
        val moves: Map<T, Int> = emptyMap(),
        val parents: Map<T, Node<T>> = emptyMap()
    )

    private data class Delta<T>(
        val addUnprocessed: Pair<Node<T>, Int>? = null,
        val removeProcessed: T? = null,
        val updateMoves: Pair<T, Int>? = null,
        val updateParents: Pair<T, Node<T>>? = null
    )

    fun shortestPath(start: Node<T>, target: Node<T>): List<Node<T>> {
        val startState = State(
            unprocessed = setOf(
                Pair(
                    start,
                    start.heuristicFunction(target)
                )
            ),
            moves = mapOf(start.id to 0)
        )
        val state = untilTargetNodeIsFound(startState, target)

        return generateSequence(target) { state.parents[it.id] }.toList().reversed()
    }

    private tailrec fun untilTargetNodeIsFound(state: State<T>, end: Node<T>): State<T> {
        if (state.unprocessed.isEmpty()) return state

        val priority = state.unprocessed.sortedWith { p1, p2 -> p1.second - p2.second }
        val (node, _) = priority.first()

        if (node.id == end.id) return state

        val updated = node.neighbours.map { edge ->
            val neighbour = nodes[edge.to]!!
            val neighbourMove = state.moves.getOrDefault(node.id, Int.MAX_VALUE) + edge.cost

            if (!state.unprocessed.map { it.first.id }
                    .contains(neighbour.id) && !state.processed.contains(neighbour.id)) {
                return@map Delta(
                    addUnprocessed = neighbour to neighbourMove + neighbour.heuristicFunction(end),
                    updateMoves = neighbour.id to neighbourMove,
                    updateParents = neighbour.id to node
                )
            }

            if (neighbourMove < state.moves.getOrDefault(neighbour.id, Int.MAX_VALUE)) {
                val delta = Delta(
                    updateMoves = neighbour.id to neighbourMove,
                    updateParents = neighbour.id to node
                )
                if (state.processed.contains(neighbour.id)) {
                    return@map delta.copy(
                        addUnprocessed = neighbour to neighbourMove + neighbour.heuristicFunction(end),
                        removeProcessed = neighbour.id
                    )
                }
                return@map delta
            }

            Delta()
        }.fold(state) { intermediate: State<T>, delta: Delta<T> ->
            State(
                unprocessed = intermediate.unprocessed + (delta.addUnprocessed?.let { setOf(it) }.orEmpty()),
                processed = intermediate.processed.filterNot { it == delta.removeProcessed }.toSet(),
                moves = intermediate.moves + (delta.updateMoves?.let { mapOf(it) }.orEmpty()),
                parents = intermediate.parents + (delta.updateParents?.let { mapOf(it) }.orEmpty()),
            )
        }

        return untilTargetNodeIsFound(
            updated.copy(
                unprocessed = updated.unprocessed.filterNot { it.first.id == node.id }.toSet(),
                processed = updated.processed + node.id
            ), end
        )
    }

}

