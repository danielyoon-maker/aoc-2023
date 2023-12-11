interface Day {
    val day: Int
    fun part1(input: List<String>): Long

    fun part2(input: List<String>): Long

    fun solve1(fileName: String, expectedOutput: Long) {
        val testOutput = part1(readInput(day, fileName))
        check(testOutput == expectedOutput) { "Expected $expectedOutput but got $testOutput" }

        println(part1(readInput(day, "input")))
    }

    fun solve2(fileName: String, expectedOutput: Long) {
        val testOutput = part2(readInput(day, fileName))
        check(testOutput == expectedOutput) { "Expected $expectedOutput but got $testOutput" }

        println(part2(readInput(day, "input")))
    }
}
