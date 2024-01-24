package ru.glassnekeep.dsl.multiple

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.MultipleElement
import kotlinx.coroutines.coroutineScope
import ru.glassnekeep.dsl.multiple.values.Values
import ru.glassnekeep.dsl.multiple.values.toValues
import ru.glassnekeep.parallel.CoroutinesConfig

class ThenTag(
    private val block: (Values) -> Int,
    parent: Element,
    config: CoroutinesConfig
) : MultipleElement(parent, config) {

    override val dispatcher = config.then

    override suspend fun process(input: Array<Int>): List<Int> {
       return coroutineScope {
           runAsync { block.invoke(input.toValues()) }
       }.await().let(::listOf)
    }
}