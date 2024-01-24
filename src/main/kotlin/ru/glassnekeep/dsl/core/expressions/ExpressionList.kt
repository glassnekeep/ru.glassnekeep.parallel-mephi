package ru.glassnekeep.dsl.core.expressions

import ru.glassnekeep.dsl.core.ListElement
import ru.glassnekeep.dsl.lists.values.ValueList
import ru.glassnekeep.dsl.lists.values.ValuesList
import ru.glassnekeep.parallel.CoroutinesConfig

class ExpressionList(config: CoroutinesConfig) : ListElement(null, config) {

    override val dispatcher = config.default

    override suspend fun process(input: List<List<Int>>): List<List<Int>> {
        return input
    }

    fun Value(values: ValuesList) = initTag(ValueList(values, this, config))
}