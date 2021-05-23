import java.io.File

class Graph(val name: String = callerName(2), init: Graph.() -> Unit = {}) {
    class Node constructor(
        /**
         * поле id никак не используется алгоритмом и носит характер "полезной нагрузки" графа
         * мы будем класть туда номера вершин для наглядности
         */
        val id: Int,
        val neighbours: MutableSet<Node> = mutableSetOf()) {

        fun connect(to: Node) = neighbours.add(to)

        fun biConnect(to: Node) {
            connect(to)
            to.connect(this)
        }
    }

    val nodes = mutableListOf<Node>()
    fun createNode(data: Int = nodes.size) = Node(data).also { nodes.add(it) }

    //создает graphviz dot файл
    fun dotFile(fileName: String = name) {
        val paths = nodes.map { from -> from.neighbours.map { to -> "${from.id} -> ${to.id}" } }
        File("dot").apply(File::mkdirs).resolve("$fileName.dot").writeText("""
digraph ${fileName.filter { it.isJavaIdentifierPart() }} {
    ${nodes.map(Node::id).joinToString("\n    ")}
    ${paths.flatten().joinToString("\n    ")}
}""")
    }

    operator fun get(i: Int) = nodes[i]

    init { init() }
}

