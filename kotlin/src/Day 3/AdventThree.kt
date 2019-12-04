import java.io.File
import java.lang.Error
import kotlin.math.abs

data class Vector(val direction: Char, val magnitude: Int)

data class Coordinate(val x: Int, val y: Int)

fun main(args: Array<String>) {
    val lines = ArrayList<String>()
    File("./src/Day 3/input").forEachLine { lines.add(it) }
    val commands =
        lines.map { vecs -> vecs.split(",").map { vec -> Vector(vec[0], Integer.parseInt(vec.substring(1))) } }

    val firstWire = commands.first()
    val secondWire = commands.last()

    val firstCoordinates = getCoordinatesOfWire(firstWire)
    val secondCoordinates = getCoordinatesOfWire(secondWire)

    val closestOverlap = firstCoordinates
        .intersect(secondCoordinates)
        .filter { it != Coordinate(0, 0) }
        .map { abs(it.x) + abs(it.y) }
        .min()

    println(closestOverlap) // Part 1 = 5357
}

fun manhattanDistanceFromOrigin(point: Coordinate): Int {
    return abs(point.x) + abs(point.y)
}


fun getCoordinatesOfWire(wire: List<Vector>): List<Coordinate> {
    return wire.fold(
        emptyList(),
        { acc, vec -> acc.plus(getCoordinatesOfVector(acc.lastOrNull() ?: Coordinate(0, 0), vec)) })
}

fun getCoordinatesOfVector(origin: Coordinate, vector: Vector): List<Coordinate> = when (vector.direction) {
    'U' -> (0..vector.magnitude).map { Coordinate(origin.x, origin.y + it) }
    'R' -> (0..vector.magnitude).map { Coordinate(origin.x + it, origin.y) }
    'D' -> (0..vector.magnitude).map { Coordinate(origin.x, origin.y - it) }
    'L' -> (0..vector.magnitude).map { Coordinate(origin.x - it, origin.y) }
    else -> throw Error("Unknown direction ${vector.direction}")
}
