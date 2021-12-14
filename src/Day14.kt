
fun main() {

    fun initialCounts(list:List<String>):Map<String, Long> {
        return (0 until list[0].length-1)
            .map { i -> list[0].substring(i,i+2) }
            .groupingBy { s -> s }
            .eachCount() as Map<String, Long>
    }

    fun middlePolymer(list:List<String>):Map<String,Pair<String, String>> {
        return list.subList(2, list.size)
            .map { line -> line.split(" -> ") }
            .associate { (org, middle) -> Pair(org, Pair(org[0] + middle, middle + org[1])) }
    }

    fun nextCounts(counts: Map<String, Long>, map: Map<String, Pair<String, String>>): Map<String, Long> {
        val counts1 = counts.map { (pair, count) -> Pair(map[pair]!!.first, count) }.asSequence()
        val counts2 = counts.map { (pair, count) -> Pair(map[pair]!!.second, count) }.asSequence()
        return (counts1 + counts2)
            .groupBy({ it.first }, { it.second })
            .mapValues { (_, values) -> values.sum() }
    }

    fun countChars(counts: Map<String, Long>, initialString: String): Map<Char, Long> {
        return (
                counts.map{(s, count) -> Pair(s[0], count)}.asSequence()
                + counts.map{(s, count) -> Pair(s[1], count)}.asSequence()
                + Pair(initialString[0], 1L)
                + Pair(initialString[initialString.length-1], 1L)
                )
            .groupBy ({ it.first }, {it.second})
            .mapValues { (_, values) -> values.sum() }
    }

    fun solve(list: List<String>, iterations:Int): Long {
        var counts = initialCounts(list)
        val map = middlePolymer(list)
        for (i in 1 .. iterations) {
            counts = nextCounts(counts, map)
        }
        val min = countChars(counts, list[0]).values.minOrNull()!!
        val max = countChars(counts, list[0]).values.maxOrNull()!!
        return (max - min)/2L
    }

    val testInput = readLinesFromFile("data/day14_test.txt")
    check(solve(testInput, 10) == 1588L)
    check(solve(testInput, 40) == 2188189693529L)

    val input = readLinesFromFile("data/day14.txt")
    println(solve(input, 10))
    println(solve(input, 40))
}





