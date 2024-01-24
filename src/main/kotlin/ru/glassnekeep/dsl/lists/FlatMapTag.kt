package ru.glassnekeep.dsl.lists

import ru.glassnekeep.dsl.core.ListElement
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.glassnekeep.parallel.CoroutinesConfig

class FlatMapTag(
    private val block: (Int) -> Int,
    parent: ListElement,
    config: CoroutinesConfig
) : ListElement(parent, config) {

    override val dispatcher = config.flatMap

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        return coroutineScope {
            input.flatMap { list ->
                list.map {
                    runAsync { block(it) }
                }.awaitAll()
            }.let(::listOf)
        }
    }
}