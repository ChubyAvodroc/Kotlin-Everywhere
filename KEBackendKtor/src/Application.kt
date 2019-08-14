package dev.chuby

import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.routing.*
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import org.slf4j.event.Level

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.TRACE
    }

    install(Authentication) {
        basicAuth()
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()

        }
    }

    install(Routing) {
        val database: Database = InMemoryDatabase

        rootApi()
        attendeesApi(database)
    }
}
