package ru.glassnekeep.dsl.core

import ru.glassnekeep.dsl.single.RecurseTag
import ru.glassnekeep.dsl.single.clauses.IfClause
import ru.glassnekeep.dsl.single.clauses.SwitchClause
import kotlinx.coroutines.CoroutineScope
import ru.glassnekeep.dsl.single.Apply
import ru.glassnekeep.parallel.CoroutinesConfig

abstract class SingleElement(
    private val parent: Element?,
    config: CoroutinesConfig,
    scope: CoroutineScope = DEFAULT_COROUTINE_SCOPE
) : Element(scope, parent, config) {

    abstract fun process(input: Int): List<Int>

    final override suspend fun processElement(): List<Int> {
        val res = parent?.processElement()?.single() ?: 0

        return process(res)
    }

    protected fun <T: Element> initTag(tag: T, init: (T.(value: Int) -> Int)? = null): T {
        //parent?.child = tag
        return tag
    }

    fun If(condition: (Int) -> Boolean, ) = initTag(IfClause(condition, this, config))

    fun Swtich(block: (Int) -> Int) = initTag(SwitchClause(block, this, config))

    fun Apply(block: (Int) -> Int) = initTag(Apply(block, this, config))

    fun Recurse(block: (Int) -> Boolean) = initTag(RecurseTag(block, this, config))
}