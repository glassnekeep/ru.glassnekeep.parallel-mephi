package ru.glassnekeep.dsl.single.clauses

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.SingleElement
import ru.glassnekeep.parallel.CoroutinesConfig

class SwitchClause(
    private val block: (Int) -> Int,
    parent: Element,
    config: CoroutinesConfig
) : SingleElement(parent, config) {

    override val dispatcher = config.default

    private val cases = mutableMapOf<Int, (Int) -> Int>()

    private var default: ((Int) -> Int)? = null

    override fun process(input: Int): List<Int> {
        val def = default ?: throw SwitchCaseInconsistencyException(MISSING_DEFAULT_BRANCH_ERROR_MSG)

        val action = cases.getOrDefault(block.invoke(input), def)
        return action.invoke(input).let(::listOf)
    }

    fun Case(value: Int, action: (Int) -> Int): CaseClause {
        cases[value] = action
        return CaseClause(value, action)
    }

    inner class CaseClause(value: Int, block: (Int) -> Int) {

        fun Case(value: Int, action: (Int) -> Int): CaseClause {
            cases[value] = action
            return CaseClause(value, action)
        }

        fun Default(action: (Int) -> Int): SingleElement {
            default = action
            return this@SwitchClause
        }
    }

    class SwitchCaseInconsistencyException(msg: String): Exception(msg)

    private companion object {
        const val MISSING_DEFAULT_BRANCH_ERROR_MSG = "Missing default branch"
    }
}