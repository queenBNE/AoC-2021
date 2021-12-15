import java.lang.Integer.min
import java.util.PriorityQueue
import kotlin.Comparator

fun main() {

    fun toGrid(lines: List<String>, timesFive:Boolean): Map<Pair<Int,Int>, Int> {
        val grid = mutableMapOf<Pair<Int,Int>, Int>()
        for(i in lines.indices) {
            for(j in lines[i].indices) {
                grid[Pair(i,j)] = lines[i].substring(j,j+1).toInt()
            }
        }
        if (!timesFive) {
            return grid
        }

        for (n in 0..4) {
            for (m in 0..4) {
                val k = n+m
                if (k > 0) {
                    for(i in lines.indices) {
                        for(j in lines[i].indices) {
                            var cost = grid[Pair(i,j)]!! + k
                            if(cost > 9) {
                                cost %= 10
                                cost ++
                            }
                            grid[Pair(n*lines.size+i,m*lines[0].length+j)] = cost
                        }
                    }
                }
            }
        }
        return grid
    }

    val neighbourOffsets = setOf(Pair(-1,0),Pair(1,0),Pair(0,1),Pair(0,-1))

    fun solve(lines:List<String>, timesFive:Boolean):Int {
        val grid = toGrid(lines, timesFive)
        val visited = mutableSetOf<Pair<Int,Int>>()
        val distances = mutableMapOf(Pair(Pair(0,0),0))
        val compareByDist: Comparator<Pair<Pair<Int,Int>, Int>> = compareBy { it.second }
        val nodesByDist = PriorityQueue(compareByDist)
        val m = if(timesFive) {5} else {1}
        val destination = Pair(m*lines.size-1,m*lines.size-1)

        nodesByDist.add(Pair(Pair(0,0), 0))
        while (!visited.contains(destination)){
            val currentDistance = nodesByDist.remove()
            if (visited.contains(currentDistance.first))
                continue
            visited.add(currentDistance.first)
            for (neighbourOffset in neighbourOffsets) {
                val neighbour = Pair(currentDistance.first.first + neighbourOffset.first, currentDistance.first.second + neighbourOffset.second)
                if(!grid.containsKey(neighbour) || visited.contains(neighbour)) {
                    continue
                }
                var tmpDist = currentDistance.second + grid[neighbour]!!
                val currDistance = distances.getOrDefault(neighbour, Int.MAX_VALUE)
                val nextDistance = min(currDistance, tmpDist)
                distances[neighbour] = nextDistance
                nodesByDist.add(Pair(neighbour, nextDistance))
            }
        }
        return distances[destination]!!
    }

    val testInput = readLinesFromFile("data/day15_test.txt")
    check(solve(testInput, false) == 40)
    check(solve(testInput, true) == 315)

    val input = readLinesFromFile("data/day15.txt")
    println(solve(input,false))
    println(solve(input, true))

}