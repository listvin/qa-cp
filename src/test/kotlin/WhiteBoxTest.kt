import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhiteBoxTest {
    lateinit var bbt: BlackBoxTests

    @BeforeEach
    fun initBBT() {
        bbt = BlackBoxTests()
    }

    @Test
    fun `n1 minimal (single-node) graph`() = bbt.`minimal (single-node) graph`()

    @Test
    fun `n2 two nodes connected`() = bbt.`two nodes connected`()

    @Test
    fun `n3 two nodes, single edge is looped on one node`() {
        val g = Graph {
            repeat(2) { createNode() }
            get(0).biConnect(get(0))
            dotFile()
        }
        assertPathNotFound(g[0], g[1])
    }

    @Test
    fun `n4 minimal disconnected graph`() = bbt.`minimal disconnected graph`()
}