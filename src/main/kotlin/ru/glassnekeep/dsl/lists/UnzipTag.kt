package ru.glassnekeep.dsl.lists

import ru.glassnekeep.dsl.core.ListElement
import ru.glassnekeep.parallel.CoroutinesConfig

class UnzipTag(parent: ListElement, config: CoroutinesConfig) : ListElement(parent, config) {

    override val dispatcher = config.unzip

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        if (input.any { it.size != 2 }) throw UnzipInconsistencyException(INVALID_SIZE_OF_LISTS)

        return input
            .map { list -> Pair(list[0], list[1]) }
            .unzip()
            .let { listOf(it.first, it.second) }
    }

    class UnzipInconsistencyException(msg: String) : Exception(msg)

    private companion object {
        const val INVALID_SIZE_OF_LISTS = "Unzip called on number of lists with not all sizes equal to 2"
    }
}