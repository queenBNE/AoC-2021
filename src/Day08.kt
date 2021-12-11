fun main() {
    val testInput = readLinesFromFile("data/day08_test.txt")
    val input = readLinesFromFile("data/day08.txt")

    fun getSignal(line: String):List<Set<Char>> {
        val signalStr = line.split(" | ")[1]
        return signalStr.split(" ").map {s -> s.toCharArray().toSet()}
    }

    fun wiringMap(line: String):Map<Set<Char>, Int> {
        val signalStr = line.split(" | ")[0]
        val digits = signalStr.split(" ").map {s -> s.toCharArray().toSet()}
        val result = mutableMapOf<Int, Set<Char>>()
        for (digit in digits) {
            when (digit.size) {
                2 -> result[1] = digit
                3 -> result[7] = digit
                4 -> result[4] = digit
                7 -> result[8] = digit
             }
        }


        for (digit in digits) {
            // Size: 5
            // 3 is the only number to contain 1
            // 2 intersected with 4 has size 2
            // 5 intersected with 4 has size 3
            if (digit.size == 5) {
                if (digit.containsAll(result[1]!!)) {
                    result[3] = digit
                } else if (digit.intersect(result[4]!!).size == 2) {
                    result[2] = digit
                } else {
                    result[5] = digit
                }
                // Size: 6
                // 6 intersected with 1 has size 1
                // 0 and 9 intersected with 1 both have size 2
                // 9 intersected with 4 has size 4
                // 0 intersected with 4 has size 3
            } else if (digit.size == 6) {
                if (digit.intersect(result[1]!!).size == 1) {
                    result[6] = digit
                } else if (digit.intersect(result[4]!!).size == 4) {
                    result[9] = digit
                } else {
                    result[0] = digit
                }
            }
        }
        val res: MutableMap<Set<Char>, Int> = mutableMapOf()
        for (r in result.entries) {
            res[r.value] = r.key
        }
        return res
    }

    fun decodeSignal(line: String): Int {
        val wiringMap = wiringMap(line)
        val signal = getSignal(line)
        var decoded = 0
        val multipliers = arrayOf(1000, 100, 10, 1)
        for (i in signal.indices) {
            decoded += wiringMap.getOrDefault(signal[i], 0) * multipliers[i]
        }
        return decoded
    }


    val uniqueDigitLengths = setOf(2,3,4,7)
    fun part1(lines: List<String>):Int {
        var count = 0
        for (line in lines) {
            val signal = getSignal(line)
            for (s in signal) {
                if (uniqueDigitLengths.contains(s.size)) {
                    count++
                }
            }
        }
        return count
    }

    fun part2(lines: List<String>):Int {
        var total = 0
        for (line in lines) {
            total += decodeSignal(line)
        }
        return total
    }

    check(part1(testInput) == 26)
    println(part1(input))

    check(part2(testInput) == 61229)
    println(part2(input))
}