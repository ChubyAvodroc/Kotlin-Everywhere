package dev.chuby

import dev.chuby.data.Error
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond


fun StatusPages.Configuration.errorHandler() {

    exception<AttendeeNotFoundException> { cause ->
        call.respond(
            HttpStatusCode.NotFound,
            Error(cause.errorCode, cause.message)
        )
    }

    exception<AuthorizationException> { cause ->
        call.respond(
            HttpStatusCode.Unauthorized,
            Error(cause.errorCode, cause.message)
        )
    }

}
