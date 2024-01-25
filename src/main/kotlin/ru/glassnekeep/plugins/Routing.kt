package ru.glassnekeep.plugins

import ru.glassnekeep.controllers.ExpressionController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.*
import kotlinx.html.*

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

fun Application.configureRouting() {
    val expController = ExpressionController(this)
    routing {
        get("/styles.css") {
            call.respondCss {
                body {
                }
                rule("#expr_input") {
                    width = 300.px
                    height = 400.px
                    textAlign = TextAlign.initial
                }
                rule("#info") {
                    display = Display.block
                    boxSizing = BoxSizing.borderBox
                }
                rule("input") {
                    display = Display.block
                    width = 200.px
                    height = 30.px
                    boxSizing = BoxSizing.borderBox
                }
            }
        }
        get("/") {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {
                        + "Expressions"
                    }
                    link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                    script {
                        "const form = document.querySelector(\"#userinfo\");\n" +
                                "async function sendData() {\n" +
                                "  const formData = new FormData(form);\n" +
                                "  try {\n" +
                                "    const response = await fetch(\"https://127.0.0.1:8080/\", {\n" +
                                "      method: \"POST\",\n" +
                                "      body: formData,\n" +
                                "    });\n" +
                                "  } catch (e) {\n" +
                                "    console.error(e);\n" +
                                "  }\n" +
                                "}\n" +
                                "form.addEventListener(\"submit\", (event) => {\n" +
                                "  event.preventDefault();\n" +
                                "  sendData();\n" +
                                "});"
                    }
                }
                body {
                    form {
                        id = "info"
                        div {
                            label {
                                htmlFor = "entry"
                            }
                            input(type = InputType.text, name = "entry") {
                                id = "expr_input"
                                required = true
                            }
                            input(type = InputType.submit) {
                                value = "Submit"
                            }
                        }
                    }
                }
            }
        }
        post {
            val result = expController.handleExpression(call)
            call.respondHtml(HttpStatusCode.OK) {
                head {

                }
                body {
                    h1 {
                        + "Результаты вычисления"
                    }
                    div {
                        + result
                    }
                }
            }
        }
    }
}