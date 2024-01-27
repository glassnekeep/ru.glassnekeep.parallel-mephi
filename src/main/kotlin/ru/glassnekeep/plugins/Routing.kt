package ru.glassnekeep.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CssBuilder
import kotlinx.html.body
import kotlinx.html.head
import ru.glassnekeep.controllers.ExpressionController

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

fun Application.configureRouting() {
    val expController = ExpressionController(this)
    routing {
        get {
            call.respondHtml(HttpStatusCode.OK) {
                head {}
                body {
                    text("Test screen")
                }
            }
        }
        post {
            val result = expController.handleExpression(call)
            call.respond(result)
        }
    }
}