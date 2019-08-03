package dev.chuby

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.rootApi() {

    get("/") {
        call.respondText(
            "Welcome to Kotlin Everywhere Mexico Tour ¯\\_(ツ)_/¯",
            contentType = ContentType.Text.Plain
        )
    }

}