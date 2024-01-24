package ru.glassnekeep.plugins

import ru.glassnekeep.controllers.ExpressionController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    val expController = ExpressionController(this)
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {
                        + "Expressions"
                    }
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
                    style("text/css") {
                        unsafe {

                        }
                    }
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