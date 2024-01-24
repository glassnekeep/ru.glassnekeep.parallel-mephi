package ru.glassnekeep.dsl.core

import kotlinx.coroutines.*
import ru.glassnekeep.dsl.core.markers.ExpressionDsl
import ru.glassnekeep.parallel.CoroutinesConfig

@ExpressionDsl
abstract class Element(
    protected val scope: CoroutineScope = DEFAULT_COROUTINE_SCOPE,
    private val parent: Element?,
    protected val config: CoroutinesConfig
) {

    abstract val dispatcher: CoroutineDispatcher

    internal var child: Element? = null

    abstract suspend fun processElement(): List<Int>

    protected fun<T> runAsync(block: suspend () -> T): Deferred<T> = scope.async(dispatcher) { block.invoke() }

    protected companion object {
        val DEFAULT_COROUTINE_SCOPE = CoroutineScope(Dispatchers.IO)
    }
}