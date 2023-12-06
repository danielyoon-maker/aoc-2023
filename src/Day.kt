interface Day {
    val day: Int
    fun part1(input: List<String>): Long

    fun part2(input: List<String>): Long

    fun solve(testCase: TestCase, runner: (List<String>) -> Long) {
        val testOutput = runner(readInput(day, testCase.fileName))
        check(testOutput == testCase.expectedOutput) { "Expected $testOutput but got ${testCase.expectedOutput}" }

        println(runner(readInput(day, "input")))
    }
    data class TestCase(val fileName: String, val expectedOutput: Long)
}