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

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
