package ru.glassnekeep.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import kotlinx.html.*
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import ru.glassnekeep.dsl.core.expressions.Expression
import ru.glassnekeep.parallel.CoroutinesConfig
import ru.glassnekeep.dsl.core.*
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

    suspend fun handleString(expression: String) : String {
        count++
        val startTime = currentTimeMillis()
        val res = with(ScriptEngineManager().getEngineByExtension("kts")) {
            val final = "import ru.glassnekeep.dsl.core.expressions.Expression\n" +
                    "import ru.glassnekeep.parallel.CoroutinesConfig\n" + expression
            eval(final).toString()
            //eval("2 + 3").toString()
        }
        //Expression.Value(3).If { x -> x < 4 }.Then { x -> x + 2 }.Else { x -> x - 3 }.processElement()
        val endTime = currentTimeMillis()
        logInfo("For request #$count: time = ${endTime - startTime}")

        return res
    }
}