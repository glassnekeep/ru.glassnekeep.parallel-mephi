package ru.glassnekeep.client

import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.glassnekeep.controllers.ExpressionController
import ru.glassnekeep.controllers.parseExpression
import java.util.*

class UIHolder(application: Application) {
    private val log = application.log
    private val controller = ExpressionController(application)

    fun run() {
        while (true) {
            log.info("Enter expression below:")
            val text = scanner.nextLine()
            scope.launch {
                delay(5000)
                log.info(controller.handleExpression(text.parseExpression()))
            }
        }
    }

    private companion object {
        private val client = Client()
        private val scanner = Scanner(System.`in`)
        private val scope = CoroutineScope(Dispatchers.IO)
    }
}