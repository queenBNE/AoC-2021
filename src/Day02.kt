import java.lang.Integer.max

fun main() {
    var input = readLinesFromFile("data/day02.txt")
    var testInput = readLinesFromFile("data/day02_test.txt")

    fun part1(lines: List<String>):Int {
        var depth = 0
        var horizontalPos = 0
        for (line in lines) {
            val (command, amount) = line.split(" ")
            when (command) {
                "forward" -> horizontalPos += amount.toInt()
                "up" -> depth = max(0, depth - amount.toInt())
                "down" -> depth += amount.toInt()
            }
        }
        return depth * horizontalPos
    }

    fun part2(lines: List<String>): Any {
        var depth = 0
        var horizontalPos = 0
        var aim = 0
        for (line in lines) {
            val (command, amount) = line.split(" ")
            when (command) {
                "forward" -> {
                    horizontalPos += amount.toInt()
                    depth += amount.toInt() * aim
                }
                "up" -> {
                    aim -= amount.toInt()
                }
                "down" -> {
                    aim += amount.toInt()
                }

            }
        }
        return depth * horizontalPos
    }

    check(part1(testInput) == 150)
    println(part1(input))

    check(part2(testInput) == 900)
    println(part2(input))
}



