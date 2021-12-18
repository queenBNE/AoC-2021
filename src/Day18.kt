import java.lang.Long.max

fun main() {

    val symbols = setOf("[","]",",")
    val regularPair = Regex("\\[(\\d+),(\\d+)\\]")
    fun explode(org:String):String {
        val allRegularPairs = regularPair.findAll(org)
        val allStartIndices = allRegularPairs.map { it.range.first }.toSet()
        var next = ""
        var depth = 0
        for (i in org.indices) {
            val char = org.substring(i, i+1)
            if (char == "[") {
                depth ++
            } else if (char == "]") {
                depth --
            }
            if (depth > 4 && allStartIndices.contains(i)) {
                val match  = allRegularPairs.filter { m -> m.range.first == i }.first()
                val (a,b) = match.destructured
                var replaced = false
                outer@for (j in 1 until i) {
                    if (!symbols.contains(org.substring(i-j,i-j+1))) {
                        var k = 0
                        while(!symbols.contains(org.substring(i-j-k,i-j-k+1))) {
                            k++
                        }
                        val replacement = org.substring(i-j-k+1, i-j+1).toInt() + a.toInt()
                        next = org.substring(0, i-j-k+1) + replacement + org.substring(i-j+1,i)
                        replaced = true
                        break@outer
                    }
                }
                if (!replaced) {
                    next = org.substring(0,i)

                }
                val last = match.range.last
                replaced = false
                outer@for (j in last until org.length) {
                    if (!symbols.contains(org.substring(j,j+1))) {
                        var k = 1
                        while(!symbols.contains(org.substring(j+k,j+k+1))) {
                            k++
                        }
                        val replacement = org.substring(j, j+k).toInt() + b.toInt()
                        next = next + "0" + org.substring(last+1, j) + replacement + org.substring(j+k,org.length)
                        replaced = true
                        break@outer
                    }
                }
                if (!replaced) {
                    next = next + 0 + org.substring(last+1,org.length)
                }
                return next
            }
        }
        return org
    }

    check(explode("[[[[[9,8],1],2],3],4]") == "[[[[0,9],2],3],4]")
    check(explode("[7,[6,[5,[4,[3,2]]]]]") == "[7,[6,[5,[7,0]]]]")
    check(explode("[[6,[5,[4,[3,2]]]],1]") == "[[6,[5,[7,0]]],3]")
    check(explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]") == "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
    check(explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]") == "[[3,[2,[8,0]]],[9,[5,[7,0]]]]")

    val bigDigit = Regex("(\\d{2,})")
    fun split(org:String):String {
        val match = bigDigit.find(org)
        if (match != null) {
            val first = match.range.first
            val last = match.range.last
            val value = match.value.toInt()
            return if (value % 2 == 0) {
                val x = value / 2
                org.substring(0,first) + "[$x,$x]" + org.substring(last+1, org.length)
            } else {
                val x = (value - 1) / 2
                val y = (value + 1) / 2
                org.substring(0,first) + "[$x,$y]" + org.substring(last+1, org.length)
            }

        }
        return org
    }

    check(split("[[[[0,7],4],[15,[0,13]]],[1,1]]") == "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]")
    check(split("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]") == "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]")

    fun reduce(org:String):String {
        var curr = ""
        var next = org
        while (next != curr) {
            var currLoop1 = ""
            while (next != currLoop1) {
                currLoop1 = next
                next = explode(currLoop1)
            }
            curr = next
            next = split(curr)
        }
        return next
    }

    check(reduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]") == "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")

    fun add(lines:List<String>):String {
        var sum = lines[0]
        for (i in 1 until lines.size) {
            val line = lines[i]
            sum = "[$sum,$line]"
            sum = reduce(sum)
        }
        return sum
    }

    check(add(readLinesFromFile("data/day18_test1.txt")) == "[[[[1,1],[2,2]],[3,3]],[4,4]]")
    check(add(readLinesFromFile("data/day18_test2.txt")) == "[[[[3,0],[5,3]],[4,4]],[5,5]]")
    check(add(readLinesFromFile("data/day18_test3.txt")) == "[[[[5,0],[7,4]],[5,5]],[6,6]]")
    check(add(readLinesFromFile("data/day18_test4.txt")) == "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
    check(add(readLinesFromFile("data/day18_test.txt")) == "[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]")

    fun magnitude(number:String):Long {
        var result = number
        while(regularPair.containsMatchIn(result)) {
            val match = regularPair.find(result)!!
            val (a, b) = match.destructured
            val magnitude = 3 * a.toLong() + 2 * b.toLong()
            result = result.substring(0, match.range.first) + magnitude + result.substring(
                match.range.last + 1,
                result.length
            )
        }
        return result.toLong()
    }

    check(magnitude("[9,1]") == 29L)
    check(magnitude("[1,9]") == 21L)
    check(magnitude("[[9,1],[1,9]]") == 129L)
    check(magnitude("[[1,2],[[3,4],5]]") == 143L)
    check(magnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]") == 1384L)
    check(magnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]") == 445L)
    check(magnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]") == 791L)
    check(magnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]") == 1137L)
    check(magnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]") == 3488L)

    fun solve1(lines:List<String>):Long {
        val sum = add(lines)
        return magnitude(sum)
    }

    fun solve2(lines:List<String>):Long {
        var maxSum = 0L
        for (i in lines.indices) {
            for (j in lines.indices) {
                if (i != j) {
                    maxSum = max(maxSum, magnitude(add(listOf(lines[i], lines[j]))))
                }
            }
        }
        return maxSum
    }

    check(solve1(readLinesFromFile("data/day18_test.txt")) == 4140L)
    println(solve1(readLinesFromFile("data/day18.txt")))

    check(solve2(readLinesFromFile("data/day18_test.txt")) == 3993L)
    println(solve2(readLinesFromFile("data/day18.txt")))

}