package ru.glassnekeep.controllers

import io.ktor.server.application.*

abstract class Controller(application: Application) {

    private val logger = application.log

    fun logError(message: String) {
        logger.error(message)
    }

    fun logInfo(message: String) {
        logger.info(message)
    }

    companion object {
        const val errorMsg = "Unknown error occurred"
    }
}