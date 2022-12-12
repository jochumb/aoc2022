package aoc2022.day12

import java.util.*

data class Node<Id>(
    val id: Id,
    val neighbours: List<Edge<Id>>,
    val heuristicFunction: (Node<Id>) -> Int
)

data class Edge<Id>(val to: Id, val cost: Int = 1)

class AStar<Id> private constructor(private val nodes: Map<Id, Node<Id>>, startId: Id, targetId: Id) {
    companion object {
        fun <Id> shortestPath(nodes: Map<Id, Node<Id>>, start: Id, target: Id): List<Node<Id>> =
            AStar(nodes, start, target).shortestPath()
    }

    private val start = nodes[startId]!!
    private val target = nodes[targetId]!!

    private val costs: MutableMap<Id, Int> = mutableMapOf(start.id to start.heuristicFunction(target))
    private val moves: MutableMap<Id, Int> = mutableMapOf(start.id to 0)
    private val parents: MutableMap<Id, Node<Id>> = mutableMapOf()
    private val processed: MutableSet<Id> = mutableSetOf()

    private val queue: PriorityQueue<Node<Id>> = PriorityQueue { node1, node2 ->
        costs.get(node1) - costs.get(node2)
    }

    private fun shortestPath(): List<Node<Id>> {
        queue.add(start)

        while (queue.isNotEmpty()) {
            val node: Node<Id> = queue.peek()
            if (node.id == target) break

            processNeighboursFor(node)

            queue.remove(node)
            processed.add(node.id)
        }

        return backtracePath()
    }

    private fun processNeighboursFor(node: Node<Id>) =
        node.neighbours.forEach { edge -> processNeighbourFor(edge, node) }

    private fun processNeighbourFor(edge: Edge<Id>, node: Node<Id>) {
        val neighbour = nodes[edge.to]!!
        val moveToNeighbour = moves.get(node) + edge.cost

        when {
            notSeen(neighbour)                     -> {
                updateState(node, neighbour, moveToNeighbour)
                queue.add(neighbour)
            }

            moveToNeighbour < moves.get(neighbour) -> {
                updateState(node, neighbour, moveToNeighbour)
                if (processed.contains(neighbour.id)) {
                    processed.remove(neighbour.id)
                    queue.add(neighbour)
                }
            }
        }
    }

    private fun notSeen(node: Node<Id>): Boolean = !queue.contains(node) && !processed.contains(node.id)

    private fun updateState(
        node: Node<Id>,
        neighbour: Node<Id>,
        moveToNeighbour: Int
    ) {
        parents[neighbour.id] = node
        moves[neighbour.id] = moveToNeighbour
        costs[neighbour.id] = moveToNeighbour + neighbour.heuristicFunction(target)
    }

    private fun backtracePath(): List<Node<Id>> = generateSequence(target) { parents[it.id] }.toList().reversed()

    private fun MutableMap<Id, Int>.get(node: Node<Id>): Int = getOrDefault(node.id, Int.MAX_VALUE)

}

