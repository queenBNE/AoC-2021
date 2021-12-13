
fun main() {
    fun getCoordinates(lines: List<String>):Set<Pair<Int,Int>> {
        val coordinates = mutableSetOf<Pair<Int,Int>>()
        for (line in lines) {
            if (line.isEmpty()) {
                return coordinates
            }
            val (x, y) = line.split(",").map {s -> s.toInt()}
            coordinates.add(Pair(x,y))
        }
        return setOf()
    }

    fun getFolds(lines: List<String>):List<Pair<String,Int>> {
        val foldLines = mutableListOf<Pair<String,Int>>()
        val regex = Regex("fold along ([a-z])=([0-9].*)")
        for (line in lines) {
            if (regex.matches(line)) {
                val (direction, number) = regex.find(line)!!.destructured
                foldLines.add(Pair(direction, number.toInt()))
            }
        }
        return foldLines
    }

    fun fold(coordinates: Set<Pair<Int, Int>>, pair: Pair<String, Int>): Set<Pair<Int, Int>> {
        val folded = mutableSetOf<Pair<Int,Int>>()
        for (coordinate in coordinates) {
            val x = if (pair.first == "x" && coordinate.first > pair.second) {
                pair.second - (coordinate.first - pair.second)
            } else {
                coordinate.first
            }
            val y = if (pair.first == "y" && coordinate.second > pair.second) {
                pair.second - (coordinate.second - pair.second)
            } else {
                coordinate.second
            }
            folded.add(Pair(x,y))
        }
        return folded
    }

    fun solve1(lines: List<String>): Int {
        val coordinates = getCoordinates(lines)
        val folds = getFolds(lines)
        val folded = fold(coordinates, folds[0])
        return folded.size
    }

    fun solve2(lines: List<String>) {
        val coordinates = getCoordinates(lines)
        val folds = getFolds(lines)
        var folded = coordinates
        for (fold in folds) {
            folded = fold(folded, fold)
        }

        val maxX = folded.map { pair -> pair.first }.maxByOrNull { x -> x }
        val maxY = folded.map { pair -> pair.second}.maxByOrNull { y -> y }

        val result = Array(maxY!! + 1) { y ->
            Array(maxX!! + 1) { x ->
                if (folded.contains(Pair(x,y))) {
                    "#"
                } else {
                    "."
                }
            }
        }

        result.forEach {
            it.forEach { it -> print("$it, ") }
            println()
        }
    }

    val testInput = readLinesFromFile("data/day13_test.txt")
    check(solve1(testInput) == 17)
    solve2(testInput)

    val input = readLinesFromFile("data/day13.txt")
    println(solve1(input))
    solve2(input)
}



