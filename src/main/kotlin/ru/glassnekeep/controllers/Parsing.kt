package ru.glassnekeep.controllers

fun String.parseExpression(): String {
    return ParsingStrategy.parse(this)
}

sealed interface ParsingStrategy {
    fun parse(expr: String): String {
        return "CoroutinesConfig.scope.async {\n" +
                expr.replace("Expression", "Expression(CoroutinesConfig.DEFAULT)") +
                "\n}"
    }

    fun fit(expr: String): Boolean

    companion object {
        private val strategies = arrayListOf(SingleValueStrategy, MultipleValuesStrategy, ParseValueListStrategy)

        fun parse(expr: String) = strategies
            .first { it.fit(expr) }
            .parse(expr)
    }
}

private object SingleValueStrategy : ParsingStrategy {

    override fun parse(expr: String): String {
        return super.parse(expr)
            .replace("[", "")
            .replace("]", "")
    }

    override fun fit(expr: String) = expr.contains("Value\\(\\d+\\)".toRegex())
}

private object MultipleValuesStrategy : ParsingStrategy {

    override fun fit(expr: String) = expr.contains("Value\\(\\d+,".toRegex())
}

private object ParseValueListStrategy : ParsingStrategy {
    override fun parse(expr: String): String {
        return "CoroutinesConfig.scope.async {\n" + expr
            .replace("Expression", "ExpressionList(CoroutinesConfig.DEFAULT)")
            .replace("Value(", "Value(ValueList(")
            .replace("])", ")))")
            .replace("[", "listOf(")
            .replace("]", ")") +
        "\n}"
    }

    override fun fit(expr: String) = expr.startsWith("Expression.Value([")
}

