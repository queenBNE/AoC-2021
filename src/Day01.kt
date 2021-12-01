fun main() {
    fun countIncrements(input: List<Int>, offset: Int): Int {
        var count = 0
        for (i in 0 until input.size - offset) {
            if (input[i+offset] > input[i]) {
                count ++
            }
        }
        return count
    }

    fun part1(input: List<Int>): Int = countIncrements(input, 1)
    fun part2(input: List<Int>): Int = countIncrements(input, 3)

    // test if implementation meets criteria from the description, like:
    val testInput = readLinesAsInt("data/day01_test.txt")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readLinesAsInt("data/day01.txt")
    println(part1(input))
    println(part2(input))
}
