fun main() {
    val day = "02"
    val maxCubes = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
    
    fun part1(input: List<String>): Int {
        return input.sumOf { row ->
            row.trim().split(":").let {
                if (it[1].trim().split(";").all {
                    !it.trim().split(",").any {
                         it.trim().split(" ").let {
                            it[0].toInt() > (maxCubes[it[1]] ?: Int.MAX_VALUE)
                        }
                    }
                }) {
                    it[0].split(" ")[1].toInt()
                } else {
                    0
                }
            }
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { row ->
            row.trim().split(":").let {
                val minCubes = mutableMapOf<String, Int>()
                it[1].trim().split(";").forEach {
                    it.trim().split(",").forEach {
                        it.trim().split(" ").let {
                            it[0].toInt().let { cubes ->
                                if (cubes > (minCubes[it[1]]?: 0)) {
                                    minCubes[it[1]] = cubes
                                }
                            }
                        }
                    }
                }
                minCubes.values.fold(1L) { prod, value ->
                    prod * value
                }
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val test1Input = readInput("Day${day}_test1")
    val answer1 = part1(test1Input)
    "answer 1: $answer1".println()
    compare(answer1, 8)

    val input = readInput("Day${day}")
    part1(input).println()
    
    // test if implementation meets criteria from the description, like:
    val test2Input = readInput("Day${day}_test2")
    val answer2 = part2(test2Input)
    "answer 2: $answer2".println()
    compare(answer2, 2286)

    part2(input).println()
}

public fun <V>compare(value: V, expeced: V): Unit {
    check(value == expeced) {
        println("Got $value while expecting $expeced")
    }
}
