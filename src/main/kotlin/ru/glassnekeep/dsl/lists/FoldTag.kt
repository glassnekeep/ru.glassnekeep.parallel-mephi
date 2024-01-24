package ru.glassnekeep.dsl.lists

import ru.glassnekeep.dsl.core.ListElement
import kotlinx.coroutines.coroutineScope
import ru.glassnekeep.parallel.CoroutinesConfig

class FoldTag(
    private val initValue: Int,
    private val block: (Int, Int) -> Int,
    parent: ListElement,
    config: CoroutinesConfig
) : ListElement(parent, config) {

    override val dispatcher = config.fold

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        val list = input.singleOrNull() ?: throw FoldClauseInconsistencyException(
            MANY_LISTS_PASSED_AS_PARAMETERS_ERROR_MSG
        )

        return coroutineScope {
            list.fold(runAsync {initValue}) { res, new ->
                runAsync { block(res.await(), new) }
            }.let { listOf(listOf(it.await())) }
        }
    }

    class FoldClauseInconsistencyException(msg: String) : Exception(msg)

    private companion object {
        const val MANY_LISTS_PASSED_AS_PARAMETERS_ERROR_MSG = "More than one list passed to `All` operation"
    }
}