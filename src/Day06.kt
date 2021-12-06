fun main() {
    var testInput = readLineAsInt("data/day06_test.txt")
    var input = readLineAsInt("data/day06.txt")

    fun populationTomorrow(currentPopulation: Map<Int, Long>) :Map<Int, Long>{
        var populationTomorrow = mutableMapOf<Int, Long>()
        for (ageGroupCount in currentPopulation.entries) {
            if (ageGroupCount.key > 0) {
                populationTomorrow[ageGroupCount.key - 1] = ageGroupCount.value
            }
        }
        if (currentPopulation.containsKey(0)) {
            if (populationTomorrow.containsKey(6)) {
                populationTomorrow[6] = populationTomorrow[6]!! + currentPopulation[0]!!
            } else {
                populationTomorrow[6] = currentPopulation[0]!!
            }
            populationTomorrow[8] = currentPopulation[0]!!
        }
        return populationTomorrow
    }

    fun countPopulationAtDay(initialPopulation: List<Int>, day: Int) : Long{
        var population = initialPopulation.groupingBy { it }.eachCount() as Map<Int, Long>
        for (i in 1..day) {
            population = populationTomorrow(population)
        }
        return population.values.sum()
    }

    check(countPopulationAtDay(testInput, 80) == 5934L)
    println(countPopulationAtDay(input, 80))

    check(countPopulationAtDay(testInput, 256) == 26984457539L)
    println(countPopulationAtDay(input, 256))
}



