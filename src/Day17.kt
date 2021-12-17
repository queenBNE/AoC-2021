fun main() {

    fun count(xRange:IntRange, yRange: IntRange):Int {
        var xMin = 0
        var sum = 0
        while (!xRange.contains(sum)) {
            xMin ++
            sum += xMin
        }
        val xMax = xRange.maxOrNull()!!
        val yMin =  yRange.minOrNull()!!
        val yMax = -1 * (yRange.minOrNull()!! + 1)
        var maxHeight = 0
        for (j in 1..yMax) {
            maxHeight += j
        }
        var totalHits = 0
        for (x in xMin..xMax) {
            for (y in yMin..yMax) {
                if(reachesRange(xRange, yRange, x, y)) {
                    totalHits++
                }
            }
        }
        return totalHits
    }

    fun maxHeight(yRange:IntRange):Int {
        val maxSpeed = -1*(yRange.minOrNull()!!+1)
        var sum = 0
        for (i in 1..maxSpeed) {
            sum += i
        }
        return sum
    }

    println(maxHeight(-10..-5))
    println(maxHeight(-93..-67))

    println(count(20..30, -10..-5))
    println(count(195..238, -93..-67))

}

fun reachesRange(xRange: IntRange, yRange: IntRange, x: Int, y: Int): Boolean {
    val xRangeMax = xRange.maxOrNull()!!
    val yRangeMin = yRange.minOrNull()!!
    var vx = x
    var vy = y
    var posX = 0
    var posY = 0
    while (posX <= xRangeMax && posY >= yRangeMin) {
        posX += vx
        posY += vy
        if (vx != 0) {vx -= 1}
        vy -= 1
        if (xRange.contains(posX) && yRange.contains(posY)) {
            return true
        }
    }
    return false
}


