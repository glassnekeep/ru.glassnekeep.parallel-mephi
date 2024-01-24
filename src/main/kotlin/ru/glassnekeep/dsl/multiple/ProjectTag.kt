package ru.glassnekeep.dsl.multiple

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.MultipleElement
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.glassnekeep.parallel.CoroutinesConfig

class ProjectTag(
    private val blocks: Array<out (Int) -> Int?>,
    parent: Element,
    config: CoroutinesConfig
) : MultipleElement(parent, config) {

    override val dispatcher = config.project

    override suspend fun process(input: Array<Int>): List<Int> {
        return coroutineScope {
            blocks.mapIndexed { index, function ->
                runAsync { function.invoke(input[index]) }
            }.awaitAll().filterNotNull()
        }
    }
}