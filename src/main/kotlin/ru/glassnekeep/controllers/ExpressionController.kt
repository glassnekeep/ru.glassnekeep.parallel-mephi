package ru.glassnekeep.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import kotlinx.html.*
import javax.script.ScriptEngineManager

class ExpressionController(application: Application) : Controller(application) {

    var count = 0

    suspend fun handleExpression(call: ApplicationCall) : String {
        count++
        val startTime = currentTimeMillis()
        val expression = call.receiveParameters()
            .getOrFail("entry")
            .parseExpression()
        val res = with(ScriptEngineManager().getEngineByExtension("kts")) {
            eval(expression).toString()
        }
        val endTime = currentTimeMillis()
        logInfo("For request #$count: time = ${endTime - startTime}")
        return res
    }

    suspend fun handleString(expression: String) : String {
        count++
        val startTime = currentTimeMillis()
        val res = with(ScriptEngineManager().getEngineByExtension("kts")) {
            eval(expression).toString()
            //eval("2 + 3").toString()
        }
        //Expression(CoroutinesConfig.build {  }).Value(3).If { x -> x < 4 }.Then { x -> x + 2 }.Else { x -> x - 3 }.processElement()
        val endTime = currentTimeMillis()
        logInfo("For request #$count: time = ${endTime - startTime}")

        return res
    }
}