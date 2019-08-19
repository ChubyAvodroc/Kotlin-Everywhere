package dev.chuby

import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic

fun Authentication.Configuration.basicAuth() {

    basic("myBasicAuth") {
        realm = "KE Ktor Server"
        validate { credentials ->
            if (credentials.name == "Chuby" && credentials.password == "Avodroc")
                UserIdPrincipal(credentials.name)
            else
                null
        }
    }
}
