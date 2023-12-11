package day03

import Day

data class Number(var value: Int, val startIndex: Int, var endIndex: Int)

object Today : Day {
    override val day: Int
        get() = 3

    private val SEARCH_COORDS: Set<Pair<Int, Int>> = setOf(
        Pair(-1, -1),
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0, -1),
        Pair(0, 1),
        Pair(1, -1),
        Pair(1, 0),
        Pair(1, 1)
    )

    private fun isPartNumber(rowNumber: Int, startIndex: Int, endIndex: Int, input: List<String>): Boolean {
        for (rowIndex in startIndex..endIndex) {
            for (offset in SEARCH_COORDS) {
                val yCoord = offset.first + rowNumber
                val xCoord = offset.second + rowIndex
                if (yCoord in input.indices && xCoord in input[rowNumber].indices) {
                    val lookingAt = input[yCoord][xCoord]
                    if (!lookingAt.isDigit() and (lookingAt != '.')) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun parseNumber(
        rowNumber: Int,
        number: Number,
        input: List<String>,
        gears: MutableMap<Pair<Int, Int>, MutableList<Long>>
    ) {
        val seenGears = mutableSetOf<Pair<Int, Int>>()
        for (rowIndex in number.startIndex..number.endIndex) {
            for (offset in SEARCH_COORDS) {
                val yCoord = offset.first + rowNumber
                val xCoord = offset.second + rowIndex
                if (yCoord in input.indices && xCoord in input[rowNumber].indices) {
                    val lookingAt = input[yCoord][xCoord]
                    if (lookingAt == '*') {
                        seenGears.add(Pair(yCoord, xCoord))
                    }
                }
            }
        }
        seenGears.forEach { coords ->
            run {
                val gearMultipliers = gears.getOrDefault(coords, mutableListOf())
                gearMultipliers.add(number.value.toLong())
                gears[coords] = gearMultipliers
            }
        }
    }

    private fun getRowCount(rowNumber: Int, row: String, input: List<String>): Long {
        val numbers = mutableListOf<Number>()
        var looking = false
        for ((i, c) in row.withIndex()) {
            if (c.isDigit()) {
                if (!looking) {
                    looking = true
                    numbers.add(Number(c.digitToInt(), i, i))
                } else {
                    numbers.last().value = numbers.last().value * 10 + c.digitToInt()
                    numbers.last().endIndex = i
                }
            } else {
                looking = false
            }
        }

        var out: Long = 0
        for (number in numbers) {
            if (isPartNumber(rowNumber, number.startIndex, number.endIndex, input)) {
                out += number.value
            }
        }
        return out
    }

    private fun parseRow(
        rowNumber: Int,
        row: String,
        input: List<String>,
        gears: MutableMap<Pair<Int, Int>, MutableList<Long>>
    ) {
        val numbers = mutableListOf<Number>()
        var looking = false
        for ((i, c) in row.withIndex()) {
            if (c.isDigit()) {
                if (!looking) {
                    looking = true
                    numbers.add(Number(c.digitToInt(), i, i))
                } else {
                    numbers.last().value = numbers.last().value * 10 + c.digitToInt()
                    numbers.last().endIndex = i
                }
            } else {
                looking = false
            }
        }

        numbers.forEach { number -> parseNumber(rowNumber, number, input, gears) }
    }

    override fun part1(input: List<String>): Long {
        return input.withIndex().sumOf { (i, row) -> getRowCount(i, row, input) }
    }

    override fun part2(input: List<String>): Long {
        val gears = mutableMapOf<Pair<Int, Int>, MutableList<Long>>()
        input.withIndex().forEach { (rowNumber, row) -> parseRow(rowNumber, row, input, gears) }
        return gears.values.filter { gearMultipliers -> gearMultipliers.size > 1 }
            .sumOf { gearMultipliers -> gearMultipliers.reduce(Long::times) }
    }
}

fun main() {
    Today.solve1("test1", 4361)
    Today.solve2("test1", 467835)
}