fun main() {
    val testInput = readLinesFromFile("data/day09_test.txt")
    val input = readLinesFromFile("data/day09.txt")

    val nbh = setOf(Pair(-1,0), Pair(0,1), Pair(1,0), Pair(0,-1))

    fun isMinimum(array: MutableList<List<Int>>, i: Int, j: Int): Boolean {
        for (nb in nbh) {
            val i2 = i+nb.first
            val j2 = j+nb.second
            if (i2 >= 0 && i2 < array.size && j2 >= 0 && j2 < array[0].size) {
                if (array[i][j] >= array[i2][j2]) {
                    return false
                }
            }
        }
        return true
    }

    fun toArray(lines:List<String>):MutableList<List<Int>> {
        val array = mutableListOf<List<Int>>()
        for (line in lines) {
            val row = mutableListOf<Int>()
            for (i in line.indices) {
                row.add(line.substring(i,i+1).toInt())
            }
            array.add(row)
        }
        return array
    }

    fun solve1(lines:List<String>):Int {
        val array = toArray(lines)
        var total = 0
        for (i in array.indices) {
            for (j in array[0].indices) {
                if(isMinimum(array, i, j)) {
                    total += array[i][j] + 1
                }
            }
        }
        return total
    }

    fun higherNeighbours(array: MutableList<List<Int>>, coordinate: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val higherNeighbours = mutableSetOf<Pair<Int,Int>>()
        for (nb in nbh) {
            val i2 = coordinate.first+nb.first
            val j2 = coordinate.second+nb.second
            if (i2 >= 0 && i2 < array.size && j2 >= 0 && j2 < array[0].size) {
                if (array[coordinate.first][coordinate.second] < array[i2][j2] && array[i2][j2] != 9) {
                    higherNeighbours.add(Pair(i2,j2))
                }
            }
        }
        return higherNeighbours
    }

    fun getBasinSize(array: MutableList<List<Int>>, i: Int, j: Int): Int {
        val coordinates = mutableSetOf(Pair(i,j))
        var prevCoordinates = mutableSetOf<Pair<Int,Int>>()
        while (coordinates != prevCoordinates) {
            prevCoordinates = mutableSetOf()
            prevCoordinates.addAll(coordinates)
            for (coordinate in prevCoordinates) {
                coordinates.addAll(higherNeighbours(array, coordinate))
            }
        }
        return coordinates.size
    }

    fun solve2(lines:List<String>):Int {
        val array = toArray(lines)
        val basinSizes = mutableListOf<Int>()
        for (i in array.indices) {
            for (j in array[0].indices) {
                if(isMinimum(array, i, j)) {
                    basinSizes.add(getBasinSize(array, i, j))
                }
            }
        }

        basinSizes.sortDescending()
        return basinSizes[0]*basinSizes[1]*basinSizes[2]

    }

    check(solve1(testInput) == 15)
    println(solve1(input))

    check(solve2(testInput) == 1134)
    println(solve2(input))
}