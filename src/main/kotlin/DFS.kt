import Graph.Node

private fun dfs(from: Node, to: Node, mutablePath: MutableList<Node>,
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
