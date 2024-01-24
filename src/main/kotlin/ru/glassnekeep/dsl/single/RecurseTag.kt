package ru.glassnekeep.dsl.single

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.SingleElement
import ru.glassnekeep.parallel.CoroutinesConfig

class RecurseTag(
    private val endCondition: (Int) -> Boolean,
    parent: Element,
    config: CoroutinesConfig
) : SingleElement(parent, config) {

    override val dispatcher = config.rec

    private var recurseStep: ((Int) -> Int)? = null

    override fun process(input: Int): List<Int> {
        val step = recurseStep ?: throw RecurseTagInconsistencyException(MISSING_RECURSE_BRANCH_ERROR_MSG)

        var res = input
        while (!endCondition(res)) {
            res = step(res)
        }
        return res.let(::listOf)
    }

    inner class RecurseStep {
        fun Recurse(action: (Int) -> Int): SingleElement {
            recurseStep = action
            return this@RecurseTag
        }
    }

    class RecurseTagInconsistencyException(msg: String) : Exception(msg)

    private companion object {
        const val MISSING_RECURSE_BRANCH_ERROR_MSG = "Missing 'Recurse' branch"
    }
}