package ru.glassnekeep.dsl.multiple.values

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.MultipleElement
import ru.glassnekeep.parallel.CoroutinesConfig

class ValueMany(
    private val values: IntArray,
    parent: Element,
    config: CoroutinesConfig
) : MultipleElement(parent, config) {

    override val dispatcher = config.default

    override suspend fun process(input: Array<Int>): List<Int> {
        return values.toList()
    }
}