package ru.glassnekeep.dsl.core

import ru.glassnekeep.dsl.multiple.EveryTag
import ru.glassnekeep.dsl.multiple.JoinTag
import ru.glassnekeep.dsl.multiple.ProjectTag
import ru.glassnekeep.dsl.multiple.ThenTag
import ru.glassnekeep.dsl.multiple.values.Values
import kotlinx.coroutines.CoroutineScope
import ru.glassnekeep.parallel.CoroutinesConfig

abstract class MultipleElement(
    private val parent: Element?,
    config: CoroutinesConfig,
    scope: CoroutineScope = DEFAULT_COROUTINE_SCOPE
) : Element(scope, parent, config) {

    abstract suspend fun process(input: Array<Int>): List<Int>

    final override suspend fun processElement(): List<Int> {
        val res = parent?.processElement() ?: listOf(0)
        return process(res.toTypedArray())
    }

    protected fun <T: Element> initTag(tag: T, vararg init: (T.(values: Array<Int>) -> Int)): T {
        return tag
    }

    fun Then(block: (Values) -> Int) = initTag(ThenTag(block, this, config))

    fun Every(vararg values: (Values) -> Int) = initTag(EveryTag(values, this, config))

    fun Project(vararg values: (Int) -> Int?) = initTag(ProjectTag(values, this, config))

    fun JoinValues(vararg values: Int) = initTag(JoinTag(values, this, config))
}