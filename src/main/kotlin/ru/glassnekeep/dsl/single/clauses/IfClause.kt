package ru.glassnekeep.dsl.single.clauses

import ru.glassnekeep.dsl.core.Element
import ru.glassnekeep.dsl.core.SingleElement
import ru.glassnekeep.parallel.CoroutinesConfig

class IfClause(
    private val condition: (Int) -> Boolean,
    parent: Element,
    config: CoroutinesConfig
) : SingleElement(parent, config) {

    override val dispatcher = config.default

    private var thenBranch: ((Int) -> Int)? = null
    private var elseBranch: ((Int) -> Int)? = null

    override fun process(input: Int): List<Int> {
        val thenBr = thenBranch ?: throw IfClauseInconsistencyException(MISSING_THEN_BRANCH_ERROR_MSG)
        val elseBr = elseBranch ?: throw IfClauseInconsistencyException(MISSING_ELSE_BRANCH_ERROR_MSG)

        return if (condition(input)) {
            thenBr(input)
        } else {
            elseBr(input)
        }.let(::listOf)
    }

    fun Then(action: (Int) -> Int): ThenClause {
        thenBranch = action
        return ThenClause()
    }

    inner class ThenClause {

        fun Else(action: (Int) -> Int): SingleElement {
            elseBranch = action
            return this@IfClause
        }
    }

    class IfClauseInconsistencyException(msg: String) : Exception(msg)

    private companion object {
        const val MISSING_THEN_BRANCH_ERROR_MSG = "Missing `Then` branch"
        const val MISSING_ELSE_BRANCH_ERROR_MSG = "Missing `Else` branch"
    }
}