package ru.glassnekeep.parallel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutinesConfig private constructor(
    var default: CoroutineDispatcher = Dispatchers.Default,
    var then: CoroutineDispatcher = Dispatchers.Default,
    var every: CoroutineDispatcher = Dispatchers.Default,
    var project: CoroutineDispatcher = Dispatchers.Default,
    var join: CoroutineDispatcher = Dispatchers.Default,
    var map: CoroutineDispatcher = Dispatchers.Default,
    var all: CoroutineDispatcher = Dispatchers.Default,
    var any: CoroutineDispatcher = Dispatchers.Default,
    var flatMap: CoroutineDispatcher = Dispatchers.Default,
    var fold: CoroutineDispatcher = Dispatchers.Default,
    var rec: CoroutineDispatcher = Dispatchers.Default,
    var zip: CoroutineDispatcher = Dispatchers.Default,
    var unzip: CoroutineDispatcher = Dispatchers.Default
) {

    companion object {
        fun build(block: CoroutinesConfig.() -> Unit) = CoroutinesConfig().apply(block)
    }
}