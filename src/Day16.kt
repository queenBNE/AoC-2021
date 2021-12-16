import java.util.*

fun main() {

    val hexToBitMap = mapOf(
        Pair("0", "0000"),
        Pair("1", "0001"),
        Pair("2", "0010"),
        Pair("3", "0011"),
        Pair("4", "0100"),
        Pair("5", "0101"),
        Pair("6", "0110"),
        Pair("7", "0111"),
        Pair("8", "1000"),
        Pair("9", "1001"),
        Pair("A", "1010"),
        Pair("B", "1011"),
        Pair("C", "1100"),
        Pair("D", "1101"),
        Pair("E", "1110"),
        Pair("F", "1111")
    )

    fun hexToBit(hex:String): Queue<Boolean> {
        val binaryString = hex
            .toCharArray()
            .map{char -> hexToBitMap[char.toString()]!!}
            .reduce{concat, element -> concat + element}
        val queue = LinkedList<Boolean>()
        for (char in binaryString) {
            queue.add(char == "1"[0])
        }
        return queue
    }

    fun binaryToInt(binary:String):Long {
        var result = 0L
        val list = mutableListOf<Long>()
        var x = 1L
        val l = binary.length - 1
        for (i in binary.indices) {
            list.add(x)
            x*=2L
        }
        for (i in binary.indices) {
            result += list[l-i] * binary.substring(i,i+1).toInt()
        }
        return result
    }

    fun bitsToInt(queue:Queue<Boolean>, numberOfBits:Int):Long {
        var versionBinaryString = ""
        for (i in 1..numberOfBits) {
            versionBinaryString += if (queue.remove()) {
                "1"
            } else {
                "0"
            }
        }
        return(binaryToInt(versionBinaryString))
    }

    fun getLiteralValue(queue:Queue<Boolean>):Long {
        var valueString = ""
        while (queue.remove()) {
            for (i in 1..4) {
                valueString += if (queue.remove()) {
                    "1"
                } else {
                    "0"
                }
            }
        }
        for (i in 1..4) {
            valueString += if (queue.remove()) {
                "1"
            } else {
                "0"
            }
        }
        return(binaryToInt(valueString))
    }

    fun countVersions(hex:String):Long {
        var totalVersions = 0L
        val binary = hexToBit(hex)
        while (binary.size >= 11) {
            totalVersions += bitsToInt(binary, 3)
            val typeID = bitsToInt(binary, 3)
            if (typeID == 4L) {
                getLiteralValue(binary)
            } else if (binary.remove()) {
                bitsToInt(binary, 11)
            } else {
                bitsToInt(binary, 15)
            }
        }
        return totalVersions
    }

    var binary = hexToBit("D2FE28")
    check(bitsToInt(binary, 3) == 6L)
    check(bitsToInt(binary, 3) == 4L)
    check(getLiteralValue(binary) == 2021L)
    check(countVersions("D2FE28") == 6L)
    check(countVersions("8A004A801A8002F478") == 16L)
    check(countVersions("620080001611562C8802118E34") == 12L)
    check(countVersions("C0015000016115A2E0802F182340") == 23L)
    check(countVersions("A0016C880162017C3686B18A3D4780") == 31L)
    println(countVersions(readLinesFromFile("data/day16.txt")[0]))

    fun operate(numbers:List<Long>, typeID:Long):Long {
        return when (typeID) {
            0L -> numbers.sum()
            1L -> numbers.reduce{prod, element -> prod * element}
            2L -> numbers.minOrNull()!!
            3L -> numbers.maxOrNull()!!
            5L -> if (numbers[0] > numbers[1]) {1L} else {0L}
            6L -> if (numbers[0] < numbers[1]) {1L} else {0L}
            7L -> if (numbers[0] == numbers[1]) {1L} else {0L}
            else -> 0
        }
    }

    fun packetValue(binary:Queue<Boolean>) :Long {
        bitsToInt(binary, 3) // version
        val typeID = bitsToInt(binary, 3)
        if (typeID == 4L) {
            return getLiteralValue(binary)
        } else if (binary.remove()) {
            val packetCount = bitsToInt(binary, 11)
            val numbers = mutableListOf<Long>()
            for (i in 1..packetCount) {
                numbers.add(packetValue(binary))
            }
            return operate(numbers, typeID)
        } else {
            val totalLength = bitsToInt(binary, 15)
            val currLength = binary.size
            val numbers = mutableListOf<Long>()
            while(currLength - binary.size < totalLength) {
                numbers.add(packetValue(binary))
            }
            return operate(numbers, typeID)
        }
    }

    check(packetValue(hexToBit("C200B40A82")) == 3L)
    check(packetValue(hexToBit("04005AC33890")) == 54L)
    check(packetValue(hexToBit("880086C3E88112")) == 7L)
    check(packetValue(hexToBit("CE00C43D881120")) == 9L)
    check(packetValue(hexToBit("D8005AC2A8F0")) == 1L)
    check(packetValue(hexToBit("F600BC2D8F")) == 0L)
    check(packetValue(hexToBit("9C005AC2F8F0")) == 0L)
    check(packetValue(hexToBit("9C0141080250320F1802104A08")) == 1L)

    println(packetValue(hexToBit(readLinesFromFile("data/day16.txt")[0])))

}