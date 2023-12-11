package day02

import Day
import kotlin.math.max

object Today : Day {
    override val day: Int
        get() = 2

    private fun isValidRound(round: String): Boolean {
        val counts = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return round.split(",").map { s -> s.trim().split(" ") }.all { s -> counts[s.last()]!! >= s.first().toInt() }
    }

    private fun minCount(rounds: List<String>): Long {
        val counts = mutableMapOf<String, Int>()
        rounds.forEach { round ->
            round.split(",").map { s -> s.trim().split(" ") }.forEach { s ->
                run {
                    val currentCount = counts[s.last()] ?: s.first().toInt()
                    counts[s.last()] = max(currentCount, s.first().toInt())
                }
            }
        }
        return counts.values.map(Int::toLong).reduce(Long::times)
    }

    private fun isValidGame(games: List<String>): Boolean {
        return games.all(this::isValidRound)
    }

    private fun gameScore(row: String): Int {
        val game = row.split(":").first().split(" ").last().toInt()
        return if (isValidGame(row.split(":").last().trim().split(";"))) game else 0
    }

    override fun part1(input: List<String>): Long {
        return input.sumOf(this::gameScore).toLong()
    }

    override fun part2(input: List<String>): Long {
        return input.map { row -> row.split(":").last().trim().split(";") }.sumOf { rounds -> minCount(rounds) }
    }
}

fun main() {
    Today.solve1("test1", 8)
    Today.solve2("test1", 2286)
}
