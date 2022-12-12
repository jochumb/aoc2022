package aoc2022.day12

import java.util.*

data class Node<T>(
    val id: T,
    val neighbours: List<Edge<T>>,
    val heuristicFunction: (Node<T>) -> Int
)

data class Edge<T>(val to: T, val cost: Int = 1)

class AStar<T>(private val nodes: Map<T, Node<T>>) : Comparator<Node<T>> {

    fun shortestPath(start: Node<T>, end: Node<T>): List<Node<T>> {
        val processed: PriorityQueue<Node<T>> = PriorityQueue(this)
        val unprocessed: PriorityQueue<Node<T>> = PriorityQueue(this)
        moves[start.id] = 0
        costs[start.id] = start.heuristicFunction(end)
        unprocessed.add(start)

        while (!unprocessed.isEmpty()) {
            val node: Node<T> = unprocessed.peek()
            if (node.id == end.id) {
                break
            }

            for (edge in node.neighbours) {
                val next = nodes[edge.to]!!
                val nextMove = moveFor(node) + edge.cost
                if (!unprocessed.contains(next) && !processed.contains(next)) {
                    parents[next.id] = node
                    moves[next.id] = nextMove
                    costs[next.id] = nextMove + next.heuristicFunction(end)
                    unprocessed.add(next)
                } else {
                    if (nextMove < moveFor(next)) {
                        parents[next.id] = node
                        moves[next.id] = nextMove
                        costs[next.id] = nextMove + next.heuristicFunction(end)
                        if (processed.contains(next)) {
                            processed.remove(next)
                            unprocessed.add(next)
                        }
                    }
                }
            }
            unprocessed.remove(node)
            processed.add(node)
        }

        return backTrace(end)
    }

    private fun backTrace(end: Node<T>): List<Node<T>> {
        var node: Node<T> = end
        val path: MutableList<Node<T>> = mutableListOf()

        while (parents[node.id] != null) {
            path.add(node)
            node = parents[node.id]!!
        }
        path.add(node)

        return path.toList().reversed()
    }

    private val costs: MutableMap<T, Int> = mutableMapOf()
    private val moves: MutableMap<T, Int> = mutableMapOf()
    private val parents: MutableMap<T, Node<T>> = mutableMapOf()

    private fun costFor(node: Node<T>) = costs.getOrDefault(node.id, Int.MAX_VALUE)
    private fun moveFor(node: Node<T>) = moves.getOrDefault(node.id, Int.MAX_VALUE)

    override fun compare(node1: Node<T>, node2: Node<T>): Int = costFor(node1) - costFor(node2)
}

