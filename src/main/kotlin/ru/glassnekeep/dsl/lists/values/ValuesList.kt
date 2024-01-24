package ru.glassnekeep.dsl.lists.values

import ru.glassnekeep.dsl.multiple.values.ImpossibleNumberOfArguments

data class ValuesList(
    val a: List<Int>,
    val b: List<Int> = emptyList(),
    val c: List<Int> = emptyList(),
    val d: List<Int> = emptyList(),
    val e: List<Int> = emptyList(),
    val f: List<Int> = emptyList()
) {
    fun toList() = listOf(a, b, c, d, e, f).filter { it.isNotEmpty() }
}

fun String.toValuesList(): ValuesList {
    if (!startsWith('(') && !startsWith('[')) error(ARRAY_MARKDOWN_PARSING_ERROR)
    if (!endsWith(')') && !endsWith(']')) error(ARRAY_MARKDOWN_PARSING_ERROR)
    if (!contains(',')) error(ARRAY_MARKDOWN_PARSING_ERROR)
    val list = split(", ").map { str ->
        str
            .removePrefix("(")
            .removeSuffix(")")
            .split(", ")
            .map { it.toInt() }
    }
    return list.toValuesList()
}

fun List<List<Int>>.toValuesList(): ValuesList {
    return when(size) {
        1 -> ValuesList(get(0))
        2 -> ValuesList(get(0), get(1))
        3 -> ValuesList(get(0), get(1), get(2))
        4 -> ValuesList(get(0), get(1), get(2), get(3))
        5 -> ValuesList(get(0), get(1), get(2), get(3), get(4))
        6 -> ValuesList(get(0), get(1), get(2), get(3), get(4), get(5))
        else -> throw ImpossibleNumberOfArguments("Введено некорректное значение списков = ${size}")
    }
}

const val ARRAY_MARKDOWN_PARSING_ERROR = "Ошибка ввода входного выражения"