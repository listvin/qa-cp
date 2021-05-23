import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import Graph.Node
import java.util.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class PaintingTests {
    @Test
    fun `minimal (single-node) graph`() {
        val g = Graph {
            createNode()
        }
        paint(g).assertIsValidPaint(1, 1)
    }

    @Test
    fun `two nodes connected`() {
        val g = Graph {
            repeat(2) { createNode() }
            get(0).biConnect(get(1))
        }
        paint(g).assertIsValidPaint(2, 1)
    }

    @Test
    fun `minimal disconnected graph`() {
        val g = Graph {
            repeat(2) { createNode() }
        }
        paint(g).assertIsValidPaint(2, 2)
    }

    @Test
    fun `the graph - size 5, 2 components`() {
        val g = Graph {
            repeat(5) { createNode() }
            get(0).biConnect(get(1))
            get(1).biConnect(get(2))
            get(3).biConnect(get(4))
            dotFile()
        }
        val p = paint(g)
        p.assertIsValidPaint(5, 2)
        Assertions.assertEquals(p[0], p[1])
        Assertions.assertEquals(p[0], p[2])
        Assertions.assertNotEquals(p[0], p[3])
        Assertions.assertEquals(p[3], p[4])
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
            }
            paint(g).assertExtensivelyCorrectPaint(g)
        }
    }
}