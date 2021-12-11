fun main() {
    val testInput = readLinesAsIntArray("data/day11_test.txt")
    val testMini = readLinesAsIntArray("data/day11_mini.txt")
    val input = readLinesAsIntArray("data/day11.txt")

    fun printNice(array: List<List<Int>>) {
        for (i in array.indices) {
            for(j in array.indices) {
                if (array[i][j] < 10) {
                    print(" ")
                }
                print(array[i][j])
                print(" ")
            }
            print("\n")
        }
        println(" ")
    }

    fun zeroes(n:Int, m:Int):MutableList<MutableList<Int>> {
        val array = mutableListOf<MutableList<Int>>()
        for (i in 0 until n) {
            val row = mutableListOf<Int>()
            for(j in 0 until m) {
                row.add(0)
            }
            array.add(row)
        }
        return array
    }

    fun willFlash(array: List<List<Int>>):Boolean {
        for (i in array.indices) {
            for(j in array.indices) {
                if (array[i][j] == 10) {
                    return true
                }
            }
        }
        return false
    }

    fun addToNeighbours(nextState: MutableList<MutableList<Int>>, i: Int, j: Int): MutableList<MutableList<Int>> {
        for (u in -1 .. 1) {
            for (v in -1 .. 1) {
                val i2 = i + u
                val j2 = j + v
                if (i2 >= 0 && i2 < nextState.size && j2 >=0 && j2 < nextState.size) {
                    if (nextState[i2][j2] == 10 && (u != 0 || v != 0)) {
                        // Only increase a 10 when it flashes!
                    } else {
                        nextState[i2][j2]++
                    }
                }
            }
        }
        return nextState
    }

    fun nextState(array: List<List<Int>>):MutableList<MutableList<Int>> {
        var nextState = zeroes(array.size, array[0].size)
        // Step one, add 1 everywhere
        for (i in array.indices) {
            for(j in array.indices) {
                nextState[i][j] = array[i][j] + 1
            }
        }
        // While there are still points that will flash
        while(willFlash(nextState)) {
            for (i in array.indices) {
                for(j in array.indices) {
                    if (nextState[i][j] == 10) {
                        nextState = addToNeighbours(nextState, i, j)
                    }
                }
            }
        }
        return nextState
    }

    fun solve1(array: List<List<Int>>, rounds:Int, print:Boolean) : Int {
        var total = 0
        var prevState = array
        for (x in 1..rounds) {
            val nextState = nextState(prevState)
            for (i in array.indices) {
                for (j in array.indices) {
                    if (nextState[i][j] >= 10) {
                        nextState[i][j] = 0
                        total++
                    }
                }
            }
            prevState = nextState
            if (print) {
                printNice(prevState)
            }
        }
        return total
    }

    fun solve2(array: List<List<Int>>) : Int {
        var round = 0
        var prevState = array
        while (true) {
            round ++
            var total = 0
            val nextState = nextState(prevState)
            for (i in array.indices) {
                for (j in array.indices) {
                    if (nextState[i][j] >= 10) {
                        nextState[i][j] = 0
                        total++
                    }
                }
            }
            if (total == 100)
                return round
            prevState = nextState
        }
    }


    check(solve1(testMini, 2, true) == 9)
    check(solve1(testInput, 10, false) == 204)
    check(solve1(testInput, 100, false) == 1656)
    println(solve1(input, 100, false))

    check(solve2(testInput) == 195)
    println(solve2(input))
}