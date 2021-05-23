import Graph.Node
import org.junit.jupiter.api.Assertions
import kotlin.test.assertEquals

fun List<Node>?.assertIsPath(source: Node, destination: Node) {
    Assertions.assertNotNull(this)
    Assertions.assertSame(source, this!!.firstOrNull())
    reduce { prev, cur ->
        Assertions.assertTrue(prev.neighbours.contains(cur)) { "No path from ${prev.id} to ${cur.id}" }
        cur
    }
    Assertions.assertSame(destination, last())
}

fun assertPathFound(from: Node, to: Node) = dfs(from, to).assertIsPath(from, to)
fun assertPathNotFound(from: Node, to: Node) = Assertions.assertNull(dfs(from, to))

fun allPairs(from: Int, until: Int, action: (a: Int, b: Int) -> Unit) {
    for (a in from until until)
        for (b in from until until)
            action(a,b)
}

fun assertValuesCount(list: List<Int>, expectedValues: Int) = assertEquals(expectedValues, list.toSet().size)

fun assertValuesInRange(list: List<Int>, from: Int, to: Int) = list.forEach {
    Assertions.assertTrue(it < to)
    Assertions.assertTrue(from <= it)
}

/**
 * checks if there are all colors for all nodesCount nodes specified
 * and all colors are numerated from 0 to count of components
 * and count of components is as expected
 */
fun List<Int>.assertIsValidPaint(nodesCount: Int, expectedComponents: Int) {
//    println(this)
    Assertions.assertEquals(nodesCount, size, "more nodes than expected")
    assertValuesCount(this, expectedComponents)
    assertValuesInRange(this, 0, expectedComponents)
}

/**
 * Tests literally nature of connection components -
 *  for every pair of nodes, nodes should be reachable from each other if they are in one
 *  component and not reachable if from others.
 *  Uses DFS path search, which is assumed to be tested with Black and White box classes
 */
fun List<Int>.assertExtensivelyCorrectPaint(g: Graph) {
    for (i in g.nodes) for (j in g.nodes) {
        if (this[i.id] == this[j.id])
            assertPathFound(i, j)
        else
            assertPathNotFound(i, j)
    }
}