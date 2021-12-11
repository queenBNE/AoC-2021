
import kotlin.collections.ArrayDeque

fun main() {
    val testInput = readLinesFromFile("data/day10_test.txt")
    val input = readLinesFromFile("data/day10.txt")

    val brackets = mapOf(Pair("]","["), Pair("}","{"), Pair(")","("), Pair(">","<"))
    val bracketValues = mapOf(Pair(")", 3L), Pair("]", 57L), Pair("}", 1197L), Pair(">",25137L))


    fun solve1(lines:List<String>) : Long {
        var total = 0L
        for (line in lines) {
            val stack:ArrayDeque<String> = ArrayDeque()
            for(i in 0 until line.length-1) {
                val bracket = line.substring(i, i+1)
                if (brackets.containsKey(bracket)) {
                    val openingBracket = stack.removeLast()
                    if (brackets[bracket] != openingBracket) {
                        total += bracketValues[bracket]!!
                    }
                } else {
                    stack.add(bracket)
                }
            }
        }
        return total
    }

    val bracketValues2 = mapOf(Pair("(", 1L), Pair("[", 2L), Pair("{", 3L), Pair("<",4L))

    fun removeCorrupted(lines:List<String>):MutableList<String> {
        val incomplete = mutableListOf<String>()
        outer@ for (line in lines) {
            val stack:ArrayDeque<String> = ArrayDeque()
            for(i in 0 until line.length-1) {
                val bracket = line.substring(i, i+1)
                if (brackets.containsKey(bracket)) {
                    val openingBracket = stack.removeLast()
                    if (brackets[bracket] != openingBracket) {
                       continue@outer
                    }
                } else {
                    stack.add(bracket)
                }
            }
            incomplete.add(line)
        }
        return incomplete
    }

    fun solve2(lines:List<String>) : Long {
        val scores = mutableListOf<Long>()
        val incompleteLines = removeCorrupted(lines)
        for (line in incompleteLines) {
            val stack:ArrayDeque<String> = ArrayDeque()
            var total = 0L
            for(i in line.indices) {
                val bracket = line.substring(i, i+1)
                if (brackets.containsKey(bracket)) {
                    stack.removeLast()
                } else {
                    stack.add(bracket)
                }
            }
            while (stack.isNotEmpty()) {
                val score = bracketValues2[stack.removeLast()]!!
                total *= 5
                total += score
            }
            scores.add(total)
        }
        scores.sort()
        return scores[(scores.size - 1)/2]
    }

    check(solve1(testInput) == 26397L)
    println(solve1(input))

    check(solve2(testInput) == 288957L)
    println(solve2(input))
}