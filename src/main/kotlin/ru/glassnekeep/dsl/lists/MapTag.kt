package ru.glassnekeep.dsl.lists

import ru.glassnekeep.dsl.core.ListElement
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.glassnekeep.parallel.CoroutinesConfig

class MapTag(
    private val block: (Int) -> Int,
    parent: ListElement,
    config: CoroutinesConfig
) : ListElement(parent, config) {

    override val dispatcher = config.map

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        return coroutineScope {
            input.single().map { value ->
                runAsync { block.invoke(value) }
            }.awaitAll().let(::listOf)
        }
    }
}