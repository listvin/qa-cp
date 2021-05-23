import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import Graph.Node
import java.util.Random

class BlackBoxTests {
    @Test
    fun `minimal (single-node) graph`() {
        val g = Graph {
            createNode()
            dotFile()
        }
        val n = g[0]
        assertPathFound(n, n)
    }

    @Test
    fun `two nodes connected`() {
        val g = Graph {
            repeat(2) { createNode() }
            get(0).biConnect(get(1))
            dotFile()
        }
        assertPathFound(g[0], g[1])
        assertPathFound(g[1], g[0])
    }

    @Test
    fun `minimal disconnected graph`() {
        val g = Graph {
            repeat(2) { createNode() }
            dotFile()
        }
        assertPathNotFound(g[0], g[1])
        assertPathNotFound(g[1], g[0])
    }

    @Test
    fun `all pairs in all bamboo graphs of length from 3 to 10`() {
        for (length in 3..10) {
            val g = Graph("bamboo of length $length") {
                val s = createNode()
                (1 until length).fold(s) { acc, _ -> createNode().apply { biConnect(acc) } }
                dotFile()
            }
            allPairs(0, length) { a, b ->
                assertPathFound(g[a], g[b])
            }
        }
    }

    @Test
    fun `all pairs in all full graphs of size from 3 to 10`() {
        for (size in 3..10) {
            val g = Graph("full graph of size $size") {
                for (i in 0 until size) createNode().apply {
                    for (j in 0 until i) biConnect(get(j))
                }
                dotFile()
            }
            allPairs(0, size) { a, b ->
                assertPathFound(g[a], g[b])
            }
        }
    }

    @Test
    fun `all pairs in all empty graphs of size from 1 to 10`() {
        for (size in 3..10) {
            val g = Graph("empty graph of size $size") {
                repeat(size, ::createNode)
                dotFile()
            }
            allPairs(0, size) { a, b ->
                if (a == b)
                    assertPathFound(g[a], g[b])
                else
                    assertPathNotFound(g[a], g[b])
            }
        }
    }

    @Test
    fun `21 random graphs of size 40 under 2% full`() {
        val (count, size, percent) = listOf(21, 40, 2)
        val edgeCreateTriesCount = size*(size-1)*percent/100
        val r = Random(239)
        repeat(count) {
            val g = Graph("random graph of size $size under $percent% full N${it+1}") {
                repeat(size, ::createNode)
                repeat(edgeCreateTriesCount) {
                    get(r.nextInt(size)).biConnect(get(r.nextInt(size)))
                }
                dotFile()
            }
            allPairs(0, size) { a, b ->
                val path = dfs(g[a], g[b])
                path?.assertIsPath(g[a], g[b])
            }
        }
    }
}