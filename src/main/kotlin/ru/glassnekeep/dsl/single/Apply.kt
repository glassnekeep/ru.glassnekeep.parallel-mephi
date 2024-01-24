package ru.glassnekeep.dsl.single

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.SingleElement
import ru.glassnekeep.parallel.CoroutinesConfig

class Apply(
    private val block: (Int) -> Int,
    parent: Element,
    config: CoroutinesConfig
) : SingleElement(parent, config) {

    override val dispatcher = config.then

    override fun process(input: Int): List<Int> {
        return block.invoke(input).let(::listOf)
    }
}