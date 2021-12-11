import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from input file and returns list of strings.
 */
fun readLinesFromFile(fileName: String): List<String>
        = File(fileName).useLines { it.toList() }

/**
 * Reads lines from input file and returns list of integers.
 */
fun readLinesAsInt(fileName: String):List<Int> {
    return readLinesFromFile(fileName).map {s -> s.toInt()}
}

fun readLineAsInt(fileName: String):List<Int> {
    val readLinesFromFile = readLinesFromFile(fileName)
    return readLinesFromFile[0].split(",").map {s -> s.toInt()}
}

fun readLinesAsIntArray(fileName: String):List<List<Int>> {
    var lines = readLinesFromFile(fileName)
    var array = mutableListOf<List<Int>>()
    for (line in lines) {
        var row = mutableListOf<Int>()
        for (i in line.indices) {
            row.add(line.substring(i, i+1).toInt())
        }
        array.add(row)
    }
    return array
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
