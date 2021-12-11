import java.lang.Integer.max
import kotlin.math.abs

fun main() {
    val input = readLinesFromFile("data/day05.txt")
    val testInput = readLinesFromFile("data/day05_test.txt")

    fun solve1(lines : List<String>, diagonal:Boolean) :Int {
        val coordinates = lines.map { line -> toCoordinate(line) }
        val boardSize = getBoardSize(coordinates)
        val board = emptyBoard(boardSize)
        for (c in coordinates) {
            if (c.first.first == c.second.first ) {
                if (c.first.second > c.second.second) {
                    for(i in c.second.second .. c.first.second) {
                        board[c.first.first][i] ++
                    }
                } else {
                    for (i in c.first.second..c.second.second) {
                        board[c.first.first][i]++
                    }
                }
            } else if (c.first.second == c.second.second) {
                if (c.first.first > c.second.first) {
                    for (i in c.second.first..c.first.first) {
                        board[i][c.first.second]++
                    }
                } else {
                    for (i in c.first.first..c.second.first) {
                        board[i][c.first.second]++
                    }
                }
            } else if (diagonal) {
                // (1,1) - (3,3) or (2,1) -- (4,3) or (2,3) -- (4,1)
                val dirX = c.second.first - c.first.first
                val dirY = c.second.second - c.first.second
                if (abs(dirX) == abs(dirY)) {
                    for (i in 0 .. abs(dirX)) {
                        board[c.first.first + i*(dirX/abs(dirX))][c.first.second+i*(dirY/abs(dirY))] ++
                    }
                }
            }
        }

        var count = 0
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] > 1) {
                    count++
                }
            }
        }
        return(count)
    }

    check(solve1(testInput, false) == 5)
    println(solve1(input, false))

    check(solve1(testInput, true) == 12)
    println(solve1(input, true))
}

fun emptyBoard(boardSize: Pair<Int,Int>): MutableList<MutableList<Int>> {
    val board = mutableListOf<MutableList<Int>>()
    for (i in 0..boardSize.second) {
        val row = mutableListOf<Int>()
        for (j in 0..boardSize.first) {
            row.add(0)
        }
        board.add(row)
    }
    return board
}

fun getBoardSize(coordinates: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>):Pair<Int,Int> {
    var maxX = 0
    var maxY = 0
    for (coordinate in coordinates) {
        maxX = max(maxX, coordinate.first.first)
        maxX = max(maxX, coordinate.second.first)
        maxY = max(maxY, coordinate.first.second)
        maxY = max(maxY, coordinate.second.second)
    }
    return Pair(maxX, maxY)
}

fun toCoordinate(s:String):Pair<Pair<Int,Int>,Pair<Int,Int>> {
    val (first, second) = s.split(" -> ")
    val (x1, y1) = first.split(",").map{str -> str.toInt()}
    val (x2, y2) = second.split(",").map{str -> str.toInt()}
    return Pair(Pair(x1,y1), Pair(x2,y2))
}