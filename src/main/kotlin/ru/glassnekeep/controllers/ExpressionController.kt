package ru.glassnekeep.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import kotlinx.html.currentTimeMillis
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
        logInfo("For request #$count: \nstart = $startTime, end = $endTime")
        return res
    }
}