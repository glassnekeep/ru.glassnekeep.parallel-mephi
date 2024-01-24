package ru.glassnekeep.dsl.core.expressions

import ru.glassnekeep.dsl.core.SingleElement
import ru.glassnekeep.dsl.multiple.values.ValueMany
import ru.glassnekeep.dsl.single.values.ValueSingle
import ru.glassnekeep.parallel.CoroutinesConfig

class Expression(config: CoroutinesConfig) : SingleElement(null, config) {

    override val dispatcher = config.default

    override fun process(input: Int): List<Int> {
        return listOf(input)
    }

    fun Value(value: Int) = initTag(ValueSingle(value, this, config))

    fun Value(vararg values: Int) = initTag(ValueMany(values, this, config))
}