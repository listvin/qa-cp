import Graph.Node

private fun dfs(from: Node, to: Node?, mutablePath: MutableList<Node>,
                visited: MutableSet<Node> = mutableSetOf()): Boolean {
    if (from === to)
        return true
    visited.add(from)
    return from.neighbours
        .firstOrNull {
            !visited.contains(it) &&
                    dfs(it, to, mutablePath, visited)
        }
        ?.let {
            mutablePath.add(it)
        } != null
}

fun dfs(from: Node, to: Node) = mutableListOf<Node>().let {
    if (dfs(from, to, it))
        it.apply { add(from) }.asReversed().toList()
    else
        null
}

fun paint(g: Graph): MutableList<Int> {
    val colors = MutableList(g.nodes.size) { -1 }

    var currentColor = 0
    for (n in g.nodes) {
        if (colors[n.id] == -1) {
            val recentlyVisited = mutableSetOf<Node>()
            dfs(n, null, mutableListOf(), recentlyVisited)
            recentlyVisited.forEach { colors[it.id] = currentColor }
            currentColor++
        }
    }

    return colors
}