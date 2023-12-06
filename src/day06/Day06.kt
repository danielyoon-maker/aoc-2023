package day06

import readInput

fun main() {
    fun winningCount(raceTime: Long, distanceToBeat: Long): Long {
        var count = 0L
        for (buttonHeld in 0..raceTime) {
            val distance = buttonHeld * (raceTime - buttonHeld)
            if (distance > distanceToBeat) {
                count += 1
            }
        }
        return count
    }

    fun part1(input: List<String>): Long {
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

    fun findFirstWin(time: Long, distanceToBeat: Long): Long {
        for (buttonHeld in 0..<time) {
            val distance = buttonHeld * (time - buttonHeld)
            if (distance > distanceToBeat) {
                return buttonHeld
            }
        }
        throw IllegalStateException("Expected win")
    }

    fun findLastWin(time: Long, distanceToBeat: Long): Long {
        for (timeRacing in 0..<time) {
            val distance = timeRacing * (time - timeRacing)
            if (distance > distanceToBeat) {
                return (time - timeRacing)
            }
        }
        throw IllegalStateException("Expected win")
    }

    fun part2(input: List<String>): Long {
        val time = input[0].split(":")[1].replace(" ", "").toLong()
        val distanceToBeat = input[1].split(":")[1].replace(" ", "").toLong()
        return findLastWin(time, distanceToBeat) - findFirstWin(time, distanceToBeat) + 1
    }

    val testInput = readInput(6, "test1")
    val realInput = readInput(6, "input")

    check(part1(testInput)==288L)

    println(part1(realInput))

    check(part2(testInput)==71503L)

    print(part2(realInput))
}