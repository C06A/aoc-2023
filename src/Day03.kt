fun main() {
    val day = "03"

    data class Symbol(val char: Char, val row: Int, val pos: Int)

    data class PartNum(var num: Int, val row: Int, val start: Int, var end: Int) {
        fun addNextDigit(char: Char) {
            num = num * 10 + char.digitToInt()
            end++
        }

        fun isSymbolAdjacent(symbol: Symbol): Boolean {
            return symbol.pos >= (start - 1)
                    && symbol.pos <= (end + 1)
                    && symbol.row >= (row - 1)
                    && symbol.row <= (row + 1)
        }
    }

    fun loadScematics(input: List<String>): Pair<List<PartNum>, List<Symbol>> {
        val numbers = mutableListOf<PartNum>()
        val symbols = mutableListOf<Symbol>()

        input.forEachIndexed { rowNum, row ->
            row.forEachIndexed { charNum, char ->
                when {
                    char == '.' -> Unit
                    char.isDigit() ->
                        if (numbers.lastOrNull().let {
                                    it != null
                                            && it.row == rowNum
                                            && (it.end + 1) == charNum
                                }) {
                            numbers.last().addNextDigit(char)
                        } else {
                            numbers += PartNum(char.digitToInt(), rowNum, charNum, charNum)
                        }

                    else -> symbols += Symbol(char, rowNum, charNum)
                }
            }
        }
        return Pair(numbers, symbols)
    }

    fun part1(input: List<String>): Int {
        val (numbers, symbols) = loadScematics(input)

        return numbers.sumOf { num ->
            if (symbols.any {
                        num.isSymbolAdjacent(it)
                    }) {
                num.num
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        val (numbers, symbols) = loadScematics(input)

        return symbols.filter {
            it.char == '*'
        }.sumOf { sym ->
            numbers.filter { num ->
                num.isSymbolAdjacent(sym)
            }.let {
                if (it.size == 2) {
                    it[0].num * it[1].num
                } else {
                    0
                }
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val test1Input = readInput("Day${day}_test")
    check(part1(test1Input) == 4361)

    val input = readInput("Day${day}")
    part1(input).println()

    // test if implementation meets criteria from the description, like:
    val test2Input = readInput("Day${day}_test")
    check(part2(test2Input) == 467835)

    part2(input).println()
}
