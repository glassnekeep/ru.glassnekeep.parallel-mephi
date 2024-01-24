package ru.glassnekeep.dsl.lists

import ru.glassnekeep.dsl.core.ListElement
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.glassnekeep.dsl.multiple.values.Values
import ru.glassnekeep.dsl.multiple.values.toValues
import ru.glassnekeep.parallel.CoroutinesConfig

class AllTag(
    private val block: (Int) -> Boolean,
    parent: ListElement,
    config: CoroutinesConfig
) : ListElement(parent, config) {

    override val dispatcher = config.all

    private var thenBranch: ((Values) -> List<Int>)? = null

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        val thenBr = thenBranch ?: throw AllClauseInconsistencyException(MISSING_THEN_BRANCH_ERROR_MSG)
        val values = input.singleOrNull() ?: throw AllClauseInconsistencyException(
            MANY_LISTS_PASSED_AS_PARAMETERS_ERROR_MSG
        )

        return coroutineScope {
            val fit = values.map { value ->
                runAsync { block.invoke(value) }
            }.awaitAll().none { !it }
            val res = if (fit) thenBr(values.toValues()) else values
            listOf(res)
        }
    }

    fun ThenDo(block: (Values) -> List<Int>) : ListElement {
        thenBranch = block
        return this
    }

    class AllClauseInconsistencyException(msg: String) : Exception(msg)

    private companion object {
        const val MISSING_THEN_BRANCH_ERROR_MSG = "Missing `Then` branch"
        const val MANY_LISTS_PASSED_AS_PARAMETERS_ERROR_MSG = "More than one list passed to `All` operation"
    }
}