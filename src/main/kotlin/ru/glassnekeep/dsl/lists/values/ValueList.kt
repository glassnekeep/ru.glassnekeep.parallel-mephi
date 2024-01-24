package ru.glassnekeep.dsl.lists.values

import ru.glassnekeep.dsl.core.ListElement
import ru.glassnekeep.parallel.CoroutinesConfig

class ValueList(
    private val values: ValuesList,
    parent: ListElement,
    config: CoroutinesConfig
) : ListElement(parent, config) {

    override val dispatcher = config.default

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        return values.toList()
    }
}