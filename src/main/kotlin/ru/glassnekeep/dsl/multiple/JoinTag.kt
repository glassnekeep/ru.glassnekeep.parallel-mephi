package ru.glassnekeep.dsl.multiple

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.MultipleElement
import ru.glassnekeep.parallel.CoroutinesConfig
import java.util.Collections.addAll

class JoinTag(
    private val values: IntArray,
    parent: Element,
    config: CoroutinesConfig
) : MultipleElement(parent, config) {

    override val dispatcher = config.join

    override suspend fun process(input: Array<Int>): List<Int> {
        return input.toMutableList().also { addAll(values.toMutableList()) }
    }
}