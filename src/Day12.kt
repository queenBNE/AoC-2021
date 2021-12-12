private val set: Set<String>
    get() {
        val visited = setOf("start")
        return visited
    }

fun main() {

    fun inputToAdjacencyMap(lines : List<String>):Map<String, Set<String>>{
        val map = mutableMapOf<String, MutableSet<String>>()
        for (line in lines) {
            val (v1, v2) = line.split("-")
            val nbh1 = map.getOrDefault(v1, mutableSetOf())
            val nbh2 = map.getOrDefault(v2, mutableSetOf())
            nbh1.add(v2)
            nbh2.add(v1)
            map[v1] = nbh1
            map[v2] = nbh2
        }
        return map
    }

    fun countPathsToEnd(adjacencyMap: Map<String, Set<String>>,
                        currentNode: String,
                        visited: Set<String>,
                        appeared:Boolean,
                        allowedTwice:String): Int {
        if (currentNode == "end") {
            return 1
        }
        var total = 0;
        val neighbours = adjacencyMap[currentNode]!!
        for (neighbour in neighbours) {
            if (!visited.contains(neighbour)) {
                total += if (neighbour[0].isLowerCase()) {
                    if (neighbour == allowedTwice && !appeared) {
                        countPathsToEnd(adjacencyMap, neighbour, visited, true, allowedTwice)
                    } else {
                        val nextVisited = mutableSetOf<String>()
                        nextVisited.addAll(visited)
                        nextVisited.add(neighbour)
                        countPathsToEnd(adjacencyMap, neighbour, nextVisited, appeared, allowedTwice)
                    }
                } else {
                    countPathsToEnd(adjacencyMap, neighbour, visited, appeared, allowedTwice)
                }
            }
        }
        return total
    }

    fun solve1(lines:List<String>):Int {
        val adjacencyMap = inputToAdjacencyMap(lines)
        return countPathsToEnd(adjacencyMap, "start", setOf("start"), true, "")
    }

    fun solve2(lines:List<String>):Int {
        val adjacencyMap = inputToAdjacencyMap(lines)
        val lowerCaseKeys = adjacencyMap.keys.filter {key -> key[0].isLowerCase()}.filter {key -> !setOf("start", "end").contains(key)}
        var total = 0
        for (key in lowerCaseKeys) {
            total += countPathsToEnd(adjacencyMap, "start", setOf("start"), false, key)
        }
        val totalWoDouble = countPathsToEnd(adjacencyMap, "start", setOf("start"), true, "")
        return total - (lowerCaseKeys.size - 1) * totalWoDouble
    }

    val testInput = readLinesFromFile("data/day12_test.txt")
    check(solve1(testInput) == 10)
    check(solve2(testInput) == 36)

    val input = readLinesFromFile("data/day12.txt")
    println(solve1(input))
    println(solve2(input))
}