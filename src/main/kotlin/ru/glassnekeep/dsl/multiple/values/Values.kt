package ru.glassnekeep.dsl.multiple.values

data class Values(
    val a: Int,
    val b: Int = 0,
    val c: Int = 0,
    val d: Int = 0,
    val e: Int = 0,
    val f: Int = 0
) {
    fun toList() = listOf(a, b, c, d, e, f).filter { it != 0 }
}

fun Array<Int>.toValues(): Values {
    return when(this.size) {
        1 -> Values(this[0])
        2 -> Values(this[0], this[1])
        3 -> Values(this[0], this[1], this[2])
        4 -> Values(this[0], this[1], this[2], this[3])
        5 -> Values(this[0], this[1], this[2], this[3], this[4])
        6 -> Values(this[0], this[1], this[2], this[3], this[4], this[5])
        else -> throw ImpossibleNumberOfArguments("Введено некорректное значение списков = ${this.size}")
    }
}

fun List<Int>.toValues(): Values {
    return when(this.size) {
        1 -> Values(this[0])
        2 -> Values(this[0], this[1])
        3 -> Values(this[0], this[1], this[2])
        4 -> Values(this[0], this[1], this[2], this[3])
        5 -> Values(this[0], this[1], this[2], this[3], this[4])
        6 -> Values(this[0], this[1], this[2], this[3], this[4], this[5])
        else -> throw ImpossibleNumberOfArguments("Введено некорректное значение списков = ${this.size}")
    }
}

class ImpossibleNumberOfArguments(msg: String) : Exception(msg)
