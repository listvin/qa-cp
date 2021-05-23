import Graph.Node
import org.junit.jupiter.api.Assertions

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