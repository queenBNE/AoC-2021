import com.sun.org.apache.xalan.internal.lib.ExsltMath.power

fun main() {
    var input = readLinesFromFile("data/day03.txt")
    var testInput = readLinesFromFile("data/day03_test.txt")

    fun part1(lines: List<String>):Double {
        var counts: MutableList<Int> = mutableListOf()
        for(i in 0 until lines[0].length) {
            counts.add(0)
        }
        for(i in lines.indices) {
            for (j in 0 until lines[i].length) {
                if (lines[i][j] == "1"[0]) {
                    counts[j] ++
                }
            }
        }

        var val1 = 0.0
        var val2 = 0.0
        for (i in counts.indices) {
            if (counts[i] > lines.size / 2) {
                val1 += power(2.0, (lines[0].length-1-i).toDouble())
            } else {
                val2 += power(2.0, (lines[0].length-1-i).toDouble())
            }
        }
        return val1*val2
    }

    fun getNext(lines: List<String>, pos:Int, mayority:Boolean): List<String>{
        var count = 0
        var linesZeroes = mutableListOf<String>()
        var linesOnes = mutableListOf<String>()
        for(line in lines) {
            if(line[pos] == "0"[0]){
                linesZeroes.add(line)
            } else {
                linesOnes.add(line)
                count++
            }
        }
        if (mayority) {
            if (count >= lines.size.toDouble() / 2) {
                return linesOnes
            }
            return linesZeroes
        }
        if (count < lines.size.toDouble() / 2) {
            return linesOnes
        }
        return linesZeroes
    }

    fun boolToDouble(s:String):Double {
        var result = 0.0
        for (i in s.indices) {
            if (s[i] == "1"[0]) {
                result += power(2.0, (s.length-1-i).toDouble())
            }
        }
        return result
    }

    fun part2(lines: List<String>):Double {
        var oxy = lines
        var pos = 0
        while (oxy.size > 1) {
            oxy = getNext(oxy, pos,true)
            pos ++
        }
        var co2 = lines
        pos = 0
        while (co2.size >1) {
            co2 = getNext(co2, pos, false)
            pos ++
        }
        return(boolToDouble(oxy[0]) * boolToDouble(co2[0]))
    }


    check(part1(testInput) == 198.00)
    println(part1(input))

    check(part2(testInput) == 230.00)
    println(part2(input))
}




