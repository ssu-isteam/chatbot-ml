package dev.isteam.chatbot.api

import dev.isteam.chatbot.dl.api.dataset.loader.MINDsLabDataSetLoader
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
    }.start(wait = true)
}
