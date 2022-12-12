package aoc2022.day12

import java.util.*

data class Node<Id>(
    val id: Id,
    val edges: List<Edge<Id>>,
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

            processEdgesForNode(node)

            queue.remove(node)
            processed.add(node.id)
        }

        return backtracePath()
    }

    private fun processEdgesForNode(node: Node<Id>) =
        node.edges.forEach { edge -> processEdgeForNode(edge, node) }

    private fun processEdgeForNode(edge: Edge<Id>, parent: Node<Id>) {
        val node = nodes[edge.to]!!
        val movesToNode = moves.get(parent) + edge.cost

        when {
            notSeen(node)                 -> {
                updateState(node, parent, movesToNode)
                queue.add(node)
            }

            movesToNode < moves.get(node) -> {
                updateState(node, parent, movesToNode)
                if (processed.contains(node.id)) {
                    processed.remove(node.id)
                    queue.add(node)
                }
            }
        }
    }

    private fun notSeen(node: Node<Id>): Boolean = !queue.contains(node) && !processed.contains(node.id)

    private fun updateState(
        node: Node<Id>,
        parent: Node<Id>,
        movesToNode: Int
    ) {
        parents[node.id] = parent
        moves[node.id] = movesToNode
        costs[node.id] = movesToNode + node.heuristicFunction(target)
    }

    private fun backtracePath(): List<Node<Id>> = generateSequence(target) { parents[it.id] }.toList().reversed()

    private fun MutableMap<Id, Int>.get(node: Node<Id>): Int = getOrDefault(node.id, Int.MAX_VALUE)

}

