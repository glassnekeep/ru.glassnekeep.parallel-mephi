package ru.glassnekeep.dsl.lists

import ru.glassnekeep.dsl.core.ListElement
import ru.glassnekeep.parallel.CoroutinesConfig

class ZipTag(parent: ListElement, config: CoroutinesConfig) : ListElement(parent, config) {

    override val dispatcher = config.zip

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        if (input.size != 2) throw ZipInconsistencyException(INVALID_NUMBER_OF_LISTS)

        return input[0].zip(input[1]).map { listOf(it.first, it.second) }
    }

    class ZipInconsistencyException(msg: String) : Exception(msg)

    private companion object {
        const val INVALID_NUMBER_OF_LISTS = "Zip called on number of lists not equal to 2"
    }
}