package ru.glassnekeep.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import javax.script.ScriptEngineManager

class ExpressionController(application: Application) : Controller(application) {

    suspend fun handleExpression(call: ApplicationCall) : String {
        val expression = call.receiveParameters()
            .getOrFail("entry")
            .parseExpression()
        return with(ScriptEngineManager().getEngineByExtension("kts")) {
            eval(expression).toString()
        }
    }
}