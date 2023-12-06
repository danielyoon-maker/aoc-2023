package day06

import Day

object Today : Day {
    override val day: Int
        get() = 6

    private fun winningCount(raceTime: Long, distanceToBeat: Long): Long {
        var count = 0L
        for (buttonHeld in 0..raceTime) {
            val distance = buttonHeld * (raceTime - buttonHeld)
            if (distance > distanceToBeat) {
                count += 1
            }
        }
        return count
    }

    override fun part1(input: List<String>): Long {
        val times = input[0].split(":")[1].trim().split("\\s+".toRegex()).map { charInt -> charInt.toInt() }
        val distances = input[1].split(":")[1].trim().split("\\s+".toRegex()).map { charInt -> charInt.toInt() }

        var winningTotal = 1L
        times.indices.forEach { idx ->
            run {
                val raceTime = times[idx].toLong()
                val distanceToBeat = distances[idx].toLong()
                winningTotal *= winningCount(raceTime, distanceToBeat)
            }
        }
        return winningTotal
    }

    private fun findFirstWin(time: Long, distanceToBeat: Long): Long {
        for (buttonHeld in 0..<time) {
            val distance = buttonHeld * (time - buttonHeld)
            if (distance > distanceToBeat) {
                return buttonHeld
            }
        }
        throw IllegalStateException("Expected win")
    }

    private fun findLastWin(time: Long, distanceToBeat: Long): Long {
        for (timeRacing in 0..<time) {
            val distance = timeRacing * (time - timeRacing)
            if (distance > distanceToBeat) {
                return (time - timeRacing)
            }
        }
        throw IllegalStateException("Expected win")
    }

    override fun part2(input: List<String>): Long {
        val time = input[0].split(":")[1].replace(" ", "").toLong()
        val distanceToBeat = input[1].split(":")[1].replace(" ", "").toLong()
        return findLastWin(time, distanceToBeat) - findFirstWin(time, distanceToBeat) + 1
    }

}

fun main() {
    Today.solve1("test1", 288L)
    Today.solve2("test1", 71503)
}
