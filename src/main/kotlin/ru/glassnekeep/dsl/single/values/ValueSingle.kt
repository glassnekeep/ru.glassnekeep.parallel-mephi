package ru.glassnekeep.dsl.single.values

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.SingleElement
import ru.glassnekeep.parallel.CoroutinesConfig

class ValueSingle(
    private val value: Int,
    parent: Element,
    config: CoroutinesConfig
) : SingleElement(parent, config) {

    override val dispatcher = config.default

    override fun process(input: Int): List<Int> {
        return listOf(value)
    }
}