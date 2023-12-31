package day01

import Day

object Today : Day {
    override val day: Int
        get() = 1

    private fun getDigitsForRow(row: String): Int {
        var digits = ""
        for (c in row) {
            if (c.isDigit()) {
                digits += c
            }
        }
        return 10 * digits.first().digitToInt() + digits.last().digitToInt()
    }

    private fun lazyGetter(mapping: Map<String, Int>, number: String): Int {
        return if (mapping.containsKey(number)) mapping[number]!! else number.first().digitToInt()
    }

    private fun getDigitsOrWordsForRow(row: String): Int {
        val wordMapping = mapOf(
            "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6,
            "seven" to 7, "eight" to 8, "nine" to 9
        )
        val numbers = wordMapping.keys.plus(arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"))
        val regex = numbers.joinToString(prefix = "(?i)", separator = "|").toRegex()
        // Regex.findAll doesn't support overlapping sequences so we just aggressively retry
        val matches = generateSequence(regex.find(row, 0)) { regex.find(row, it.range.first + 1) }
        return 10 * lazyGetter(wordMapping, matches.first().value) + lazyGetter(wordMapping, matches.last().value)
    }

    override fun part1(input: List<String>): Long {
        return input.sumOf(this::getDigitsForRow).toLong()
    }

    override fun part2(input: List<String>): Long {
        return input.sumOf(this::getDigitsOrWordsForRow).toLong()
    }
}

fun main() {
    Today.solve1("test1", 142)
    Today.solve2("test2", 281)
}
