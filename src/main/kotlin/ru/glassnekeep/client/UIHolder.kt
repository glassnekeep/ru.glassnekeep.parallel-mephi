package ru.glassnekeep.client

import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class UIHolder(application: Application) {
    private val log = application.log

    fun run() {
        while (true) {
            log.info("Enter expression below:")
            val text = scanner.nextLine()
            scope.launch {
                log.info(client.sendExpression(text).toString())
            }
        }
    }

    private companion object {
        private val client = Client()
        private val scanner = Scanner(System.`in`)
        private val scope = CoroutineScope(Dispatchers.IO)
    }
}