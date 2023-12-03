import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val digMap = mapOf(
            "zero" to 0,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
    )
    val digFirstPattern = "([0-9]|${digMap.keys.joinToString("|")})"
    val digLastPattern = "([0-9]|${digMap.keys.map { it.reversed() }.joinToString("|")})"

    fun digConvert(str: String): Int {
        return if (str.length == 1) {
            str.toInt()
        } else {
            digMap[str] ?: 0
        }
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { sum, str ->
            val dec = "^[^0-9]*(?<d>[0-9])".toRegex().find(str)!!.groups["d"]?.value ?: "0"
            val uni = "(?<u>[0-9])[^0-9]*$".toRegex().find(str)!!.groups["u"]?.value ?: "0"
            sum + dec.toInt() * 10 + uni.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { sum, str ->
            val dec = digFirstPattern.toRegex().find(str)!!.value
            val uni = digLastPattern.toRegex().find(str.reversed())!!.value
            sum + digConvert(dec?:"0") * 10 + digConvert(uni.reversed()?:"0")
        }
    }

    // test if implementation meets criteria from the description, like:
    val test1Input = readInput("Day01_test1")
    check(part1(test1Input) == 142)

    // test if implementation meets criteria from the description, like:
    val test2Input = readInput("Day01_test2")
    check(part2(test2Input) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
