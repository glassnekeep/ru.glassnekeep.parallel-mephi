package ru.glassnekeep.dsl.core

import kotlinx.coroutines.*
import ru.glassnekeep.dsl.core.markers.ExpressionDsl
import ru.glassnekeep.dsl.lists.*
import ru.glassnekeep.parallel.CoroutinesConfig

@ExpressionDsl
abstract class ListElement(
    private val parent: ListElement?,
    protected val config: CoroutinesConfig,
    protected val scope: CoroutineScope = DEFAULT_COROUTINE_SCOPE
) {

    abstract val dispatcher: CoroutineDispatcher

    private val children: ListElement? = null

    abstract suspend fun process(input: List<List<Int>>): List<List<Int>>

    suspend fun processElement(): List<List<Int>> {
        return runAsync {
            val res = children?.processElement() ?: listOf(listOf(0))
            process(res)
        }.await()
    }

    protected fun <T: ListElement> initTag(tag: T, vararg init: (T.(values: List<List<Int>>) -> List<Int>)) : T {
        //parent?.children?.add(tag)
        return tag
    }

    protected fun<T> runAsync(block: suspend () -> T): Deferred<T> = scope.async(dispatcher) { block.invoke() }

    fun Map(block: (Int) -> Int) = initTag(MapTag(block, this, config))

    fun All(block: (Int) -> Boolean) = initTag(AllTag(block, this, config))

    fun Any(block: (Int) -> Boolean) = initTag(AnyTag(block, this, config))

    fun FlatMap(block: (Int) -> Int) = initTag(FlatMapTag(block, this, config))

    fun Fold(init: Int, block: (Int, Int) -> Int) = initTag(FoldTag(init, block, this, config))

    fun Zip() = ZipTag(this, config)

    fun Unzip() = UnzipTag(this, config)

    protected companion object {
        val DEFAULT_COROUTINE_SCOPE = CoroutineScope(Dispatchers.IO)
    }
}