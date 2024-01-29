package ru.glassnekeep.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.html.*
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import ru.glassnekeep.dsl.core.expressions.Expression
import ru.glassnekeep.parallel.CoroutinesConfig
import javax.script.ScriptEngineManager

class ExpressionController(application: Application) : Controller(application) {

    var count = 0
    val loader = Thread.currentThread().contextClassLoader
    val engine by lazy {
        ScriptEngineManager(loader).getEngineByExtension("kts")
    }

    init {
        setIdeaIoUseFallback()
    }

    suspend fun handleExpression(call: ApplicationCall) : String {
        count++
        val startTime = currentTimeMillis()
        val expression = call.receiveParameters()
            .getOrFail("entry")
            .parseExpression()
        val res = engine.eval(expression).toString()
        val endTime = currentTimeMillis()
        logInfo("For request #$count: time = ${endTime - startTime}")
        return res
    }

    suspend fun handleExpression(expression: String) : String {
        count++
        val startTime = currentTimeMillis()
        logInfo("For request #$count: startTime = $startTime")
        val res = with(ScriptEngineManager().getEngineByExtension("kts")) {
            val final = imports + "\n" + expression
            eval(final)
        } as Deferred<*>
        val result = res.await()
        val endTime = currentTimeMillis()
        logInfo("For request #$count: time = ${endTime - startTime}")
        return result
            .toString()
            .replace("[", "")
            .replace("]", "")
    }

    private companion object {
        const val EXPRESSION = "import ru.glassnekeep.dsl.core.expressions.Expression"
        const val EXPRESSION_LIST = "import ru.glassnekeep.dsl.core.expressions.ExpressionList"
        const val COROUTINES_CONFIG = "import ru.glassnekeep.parallel.CoroutinesConfig"
        const val ASYNC = "import kotlinx.coroutines.async"
        const val POW = "import org.jetbrains.kotlin.com.google.common.math.IntMath.pow"
        const val VALUE_LIST = "import ru.glassnekeep.dsl.lists.values.ValuesList"
        const val LIST = "import ru.glassnekeep.dsl.lists.values.ValueList"

        val imports = listOf(EXPRESSION, EXPRESSION_LIST, COROUTINES_CONFIG, ASYNC, POW, VALUE_LIST, LIST)
            .joinToString(separator = "\n")
    }
}