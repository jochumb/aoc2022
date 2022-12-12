package aoc2022.day12

import java.util.*

data class Node<T>(
    val id: T,
    val neighbours: List<Edge<T>>,
    val heuristicFunction: (Node<T>) -> Int
)

data class Edge<T>(val to: T, val cost: Int = 1)

class AStar<T>(private val nodes: Map<T, Node<T>>) {

    fun shortestPath(start: Node<T>, end: Node<T>): List<Node<T>> {
        val costs: MutableMap<T, Int> = mutableMapOf()
        val moves: MutableMap<T, Int> = mutableMapOf()
        val parents: MutableMap<T, Node<T>> = mutableMapOf()

        val comparator: Comparator<Node<T>> = Comparator { node1, node2 ->
            costs.getOrDefault(node1.id, Int.MAX_VALUE) - costs.getOrDefault(node2.id, Int.MAX_VALUE)
        }

        val processed: MutableSet<T> = mutableSetOf()
        val unprocessed: PriorityQueue<Node<T>> = PriorityQueue(comparator)

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
                val nextMove = moves.getOrDefault(node.id, Int.MAX_VALUE) + edge.cost
                if (!unprocessed.contains(next) && !processed.contains(next.id)) {
                    parents[next.id] = node
                    moves[next.id] = nextMove
                    costs[next.id] = nextMove + next.heuristicFunction(end)
                    unprocessed.add(next)
                } else {
                    if (nextMove < moves.getOrDefault(next.id, Int.MAX_VALUE)) {
                        parents[next.id] = node
                        moves[next.id] = nextMove
                        costs[next.id] = nextMove + next.heuristicFunction(end)
                        if (processed.contains(next.id)) {
                            processed.remove(next.id)
                            unprocessed.add(next)
                        }
                    }
                }
            }

            unprocessed.remove(node)
            processed.add(node.id)
        }

        return generateSequence(end) { parents[it.id] }.toList().reversed()
    }

}

