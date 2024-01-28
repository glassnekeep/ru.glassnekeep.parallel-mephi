package ru.glassnekeep

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.glassnekeep.client.UIHolder
import ru.glassnekeep.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    UIHolder(this).run()
//    runBlocking {
//        val config = CoroutinesConfig.build {  }
//        val res = Expression.Value(3).If { x -> x < 4 }.Then { x -> x + 2 }.Else { x -> x - 3 }.processElement().single()
//        log.info("result = $res")
//    }
}