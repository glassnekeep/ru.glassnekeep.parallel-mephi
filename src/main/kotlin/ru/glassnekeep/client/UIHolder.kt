package ru.glassnekeep.client

import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.glassnekeep.controllers.ExpressionController
import java.util.*

class UIHolder(application: Application) {
    private val log = application.log
    private val controller = ExpressionController(application)

    fun run() {
        while (true) {
            log.info("Enter expression below:")
            val text = scanner.nextLine()
            scope.launch {
                //log.info(client.sendExpression(text).toString())
                log.info(controller.handleString(text))
            }
        }
    }

    private companion object {
        private val client = Client()
        private val scanner = Scanner(System.`in`)
        private val scope = CoroutineScope(Dispatchers.IO)
    }
}