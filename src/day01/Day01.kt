package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
       return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("resources/day01/day01_test1")
    check(part1(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
