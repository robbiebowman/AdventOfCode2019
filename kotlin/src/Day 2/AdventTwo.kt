import java.io.File
import java.lang.Error

fun main(args: Array<String>) {
    var line = ""
    File("./src/Day 2/input").forEachLine { line = it }
    val commands = line.split(",").map { Integer.parseInt(it) }

    println(executeAllOperations(commands, 0).first()) // Part 1 = 2842648

    val resultPairs = (0..99).map { x ->
        (0..99).map { y ->
            val remainingCommands = commands.subList(3, commands.size)
            Pair(
                "$x, $y",
                executeAllOperations(listOf(1, x, y).plus(remainingCommands), 0)
            )
        }
    }.flatten()

    val result = resultPairs.filter { it.second.first() == 19690720 }
    println(result.first()) // Part 2 = 9074

}

fun executeOpCode(numbers: List<Int>, pos: Int): Int {
    val command = opCodeToCommand(numbers[pos])
    return command(numbers[numbers[pos + 1]], numbers[numbers[pos + 2]])
}

fun executeAllOperations(numbers: List<Int>, pos: Int): List<Int> {
    if (numbers[pos] == 99) return numbers
    val result = executeOpCode(numbers, pos)
    val newNumbers =
        numbers
            .subList(0, numbers[pos + 3])
            .plus(result)
            .plus(numbers.subList(numbers[pos + 3] + 1, numbers.size))
    return executeAllOperations(newNumbers, pos + 4);
}

fun opCodeToCommand(opCode: Int): (Int, Int) -> Int {
    if (opCode == 1) return { x, y -> x + y }
    if (opCode == 2) return { x, y -> x * y }
    throw Error("Unknown OpCode $opCode")
}
