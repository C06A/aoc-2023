fun main() {
    val day = "04"
    fun part1(input: List<String>): Int {
        return input.sumOf { row ->
            row.split(':')[1]
                    .split('|')
                    .map {
                        it.trim()
                                .split(Regex(" +"))
                                .map {
                                    it.toInt()
                                }
                    }.let { (win, set) ->
                        win.fold(null) { points: Int?, value ->
                            if (set.contains(value)) points?.let { 2 * (it ?: 1) } ?: 1 else points
                        } ?: 0
                    }
        }
    }

    fun part2(input: List<String>): Int {
        return input.foldIndexed(MutableList<Int>(input.size, { 1 })) { indx, copies, row ->
            row.split(':')[1]
                    .split('|')
                    .map {
                        it.trim()
                                .split(Regex(" +"))
                                .map {
                                    it.toInt()
                                }
                    }.let { (win, set) ->
                        win.intersect(set).size
                    }.let {
                        (1..it).forEach {
                            copies[indx + it] += copies[indx]
                        }
                    }
            copies
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val test1Input = readInput("Day${day}_test")!!
    compare(part1(test1Input), 13)

    val input = readInput("Day${day}")!!
    part1(input).println()

    // test if implementation meets criteria from the description, like:
    val test2Input = readInput("Day${day}_test2") ?: test1Input
    compare(part2(test2Input), 30)

    part2(input).println()
}
