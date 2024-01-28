package ru.glassnekeep.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class Client {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }

    suspend fun sendExpression(expression: String): Int {
        return client.runCatching {
            get(BASIC_URL) {
                contentType(ContentType.Application.Json)
                setBody(expression)
            }.body<Int>()
        }.onFailure {
            println("Expression sending error!")
            throw it
        }.getOrNull() ?: 0
    }

    private companion object {
        const val BASIC_URL = "http://127.0.0.1:8080/"
        const val URL = "http://10.0.2.2:8080/"
        const val LOCAL_URL = "http://0.0.0.0:8080/"
    }
}