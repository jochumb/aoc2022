package aoc2022.day12

import java.util.*

data class Node<Id>(
    val id: Id,
    val edges: List<Edge<Id>>,
    val heuristicFunction: (Node<Id>) -> Int
)

data class Edge<Id>(val to: Id, val cost: Int = 1)

object AStar {

    fun <Id> shortestPath(nodes: Map<Id, Node<Id>>, startId: Id, targetId: Id): List<Node<Id>> {
        val start = nodes[startId]!!
        val target = nodes[targetId]!!

        val startState = State(
            queue = PriorityQueue { p1, p2 -> p1.second - p2.second },
            moves = mutableMapOf(start.id to 0)
        ).enqueue(start, start.heuristicFunction(target))

        val endState = untilTargetNodeIsFound(startState, nodes, target)

        return backtracePath(target, endState)
    }

    private tailrec fun <Id> untilTargetNodeIsFound(
        state: State<Id>,
        nodes: Map<Id, Node<Id>>,
        target: Node<Id>
    ): State<Id> {
        if (state.queue.isEmpty()) return state

        val (node, _) = state.queue.peek()

        if (node.id == target.id) return state

        return untilTargetNodeIsFound(
            state = node.edges.fold(state) { acc, edge ->
                processEdge(acc, edge, node, nodes, target)
            }.dequeue(node.id).process(node.id),
            nodes = nodes,
            target = target
        )
    }

    private fun <Id> processEdge(
        state: State<Id>,
        edge: Edge<Id>,
        parent: Node<Id>,
        nodes: Map<Id, Node<Id>>,
        target: Node<Id>
    ): State<Id> {
        val node = nodes[edge.to]!!
        val movesToNode = state.moves.get(parent) + edge.cost

        return when {
            notSeen(node.id, state)             -> state
                .enqueue(node, movesToNode + node.heuristicFunction(target))
                .movesToNode(node.id, movesToNode)
                .nodeParent(node.id, parent)

            movesToNode < state.moves.get(node) -> {
                val newState = state
                    .movesToNode(node.id, movesToNode)
                    .nodeParent(node.id, parent)
                if (state.isProcessed(node.id)) {
                    return newState
                        .enqueue(node, movesToNode + node.heuristicFunction(target))
                        .unprocess(node.id)
                }
                newState
            }

            else                                -> state
        }
    }

    private fun <Id> notSeen(id: Id, state: State<Id>): Boolean =
        !state.isQueued(id) && !state.isProcessed(id)

    private fun <Id> backtracePath(target: Node<Id>, state: State<Id>): List<Node<Id>> =
        generateSequence(target) { state.parents[it.id] }.toList().reversed()

    private fun <Id> Map<Id, Int>.get(node: Node<Id>): Int = getOrDefault(node.id, Int.MAX_VALUE)

    private class State<Id>(
        val queue: PriorityQueue<Pair<Node<Id>, Int>>,
        val processed: MutableSet<Id> = mutableSetOf(),
        val moves: MutableMap<Id, Int> = mutableMapOf(),
        val parents: MutableMap<Id, Node<Id>> = mutableMapOf()
    ) {
        fun isQueued(id: Id): Boolean =
            queue.any { it.first.id == id }

        fun enqueue(node: Node<Id>, cost: Int): State<Id> {
            queue.add(node to cost)
            return this
        }

        fun dequeue(id: Id): State<Id> {
            queue.remove(queue.first { it.first.id == id })
            return this
        }

        fun isProcessed(id: Id): Boolean =
            processed.contains(id)

        fun process(id: Id): State<Id> {
            processed.add(id)
            return this
        }

        fun unprocess(id: Id): State<Id> {
            processed.remove(id)
            return this
        }

        fun movesToNode(id: Id, count: Int): State<Id> {
            moves[id] = count
            return this
        }

        fun nodeParent(id: Id, parent: Node<Id>): State<Id> {
            parents[id] = parent
            return this
        }
    }
}

