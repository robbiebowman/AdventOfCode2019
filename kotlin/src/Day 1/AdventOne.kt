import java.io.File

fun main(args: Array<String>) {
    val moduleMasses = mutableListOf<Int>()
    File("./src/Day 1/input").forEachLine { moduleMasses.add(Integer.parseInt(it)) }
    val fuels = moduleMasses.map { massToFuelRequirement(it) }
    val moduleFuelReq = fuels.sum()
    println(moduleFuelReq) // Part 1 = 3267890

    val totalFuelRequirements = moduleFuelReq + fuels.map{fuelMassFuelRequirements(it)}.sum()
    println(totalFuelRequirements) // Part 2 = 1633903 + 3267890 = 4898972
}

fun fuelMassFuelRequirements(fuelMass: Int): Int {
    if (fuelMass <= 0) return 0;
    else {
        val fuelReq = massToFuelRequirement(fuelMass)
        return fuelReq + fuelMassFuelRequirements(fuelReq)
    }
}

fun massToFuelRequirement(mass: Int): Int {
    val fuel = (mass / 3) - 2
    return if (fuel <= 0) 0 else fuel
}