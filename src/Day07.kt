import java.lang.Integer.max
import kotlin.math.min
import kotlin.math.abs

fun main() {
    val testInput = readLineAsInt("data/day07_test.txt")
    val input = readLineAsInt("data/day07.txt")

    fun fuelNeeded(posA:Int, posB:Int, newRule:Boolean):Int {
        val dist = abs(posA - posB)
        if (!newRule) {
            return dist
        }
        var fuel = 0
        for (i in 1..dist) {
            fuel += i
        }
        return fuel
    }

    fun getFuelNeeded(positions: List<Int>, destination: Int, newRule:Boolean): Int {
        var total = 0
        for (pos in positions) {
            total += fuelNeeded(pos, destination, newRule)
        }
        return total
    }

    fun minimumFuelNeeded(positions: List<Int>, newRule:Boolean) :Int{
        var minFuel = Int.MAX_VALUE
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        for (pos in positions) {
            min = min(min, pos)
            max = max(max, pos)
        }
        for (destination in min..max) {
            minFuel = min(minFuel, getFuelNeeded(positions, destination, newRule))
        }
        return minFuel
    }

    check(minimumFuelNeeded(testInput, false) == 37)
    println(minimumFuelNeeded(input, false))

    check(fuelNeeded(16, 5, true) == 66)

    check(minimumFuelNeeded(testInput, true) == 168)
    println(minimumFuelNeeded(input, true))

}



