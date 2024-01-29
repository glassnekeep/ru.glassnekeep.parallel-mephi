package ru.glassnekeep

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.kotlin.com.google.common.math.IntMath.pow
import ru.glassnekeep.client.UIHolder
import ru.glassnekeep.dsl.core.expressions.Expression
import ru.glassnekeep.dsl.core.expressions.ExpressionList
import ru.glassnekeep.dsl.lists.values.ValuesList
import ru.glassnekeep.parallel.CoroutinesConfig
import ru.glassnekeep.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    UIHolder(this).run()
}