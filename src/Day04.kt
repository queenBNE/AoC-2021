fun main(){
    var testInput = readLinesFromFile("data/day04_test.txt")
    var input = readLinesFromFile("data/day04.txt" )

    fun linesToSets(strings: List<String>): List<MutableSet<Int>> {
        var result:MutableList<MutableSet<Int>> = mutableListOf()
        for (i in 0..4) {
            result.add(mutableSetOf())
        }
        for (i in strings.indices) {
            var list = strings[i].trim().split(Regex("\\s+")).map {str -> str.toInt()}
            result.add(list.toMutableSet())
            for (j in 0..4) {
                result[j].add(list[j])
            }
        }
        return result
    }

    fun getBingoInput (lines: List<String>): Pair<List<Int>, MutableList<List<MutableSet<Int>>>> {
        var draws:List<Int> = lines[0].split(",").map { str -> str.toInt() }
        var cards:MutableList<List<MutableSet<Int>>> = mutableListOf()
        for (i in 1 until lines.size - 2) {
            if (lines[i].isEmpty()) {
                cards.add(linesToSets(lines.subList(i+1, i+6)))
            }
        }
        return Pair(draws, cards)
    }

    fun part1(bingoInput: Pair<List<Int>, MutableList<List<MutableSet<Int>>>>):Int {
        for (i in bingoInput.first.indices) {
            var drawn = bingoInput.first[i]
            for (j in bingoInput.second.indices) {
                for (set in bingoInput.second[j]) {
                    set.remove(drawn)
                    if (set.isEmpty()) {
                        var all = mutableSetOf<Int>()
                        for (set_ in bingoInput.second[j]) {
                            all.addAll(set_)
                        }
                        all.removeAll(bingoInput.first.subList(0, i+1).toSet())
                        return all.sum() * drawn
                    }
                }
            }
        }
        return 0
    }

    fun part2(bingoInput: Pair<List<Int>, MutableList<List<MutableSet<Int>>>>):Int {
        var winTurnScore = mutableMapOf<Int,Pair<Int,Int>>()
        for (i in bingoInput.first.indices) {
            var drawn = bingoInput.first[i]
            for (j in bingoInput.second.indices) {
                for (set in bingoInput.second[j]) {
                    set.remove(drawn)
                    if (set.isEmpty() && !winTurnScore.containsKey(j)) {
                        var all = mutableSetOf<Int>()
                        for (set_ in bingoInput.second[j]) {
                            all.addAll(set_)
                        }
                        all.removeAll(bingoInput.first.subList(0, i+1).toSet())
                        winTurnScore[j] = Pair(i, all.sum()*drawn)
                    }
                }
            }
        }
        var maxTurn = -1;
        var maxTurnScore = 0;
        for (turnScore in winTurnScore.values) {
            if (turnScore.first > maxTurn) {
                maxTurn = turnScore.first
                maxTurnScore = turnScore.second
            }
        }
        return maxTurnScore
    }

    check(part1(getBingoInput(testInput)) == 4512)
    println(part1(getBingoInput(input)))

    check(part2(getBingoInput(testInput)) == 1924)
    println(part2(getBingoInput(input)))
}





