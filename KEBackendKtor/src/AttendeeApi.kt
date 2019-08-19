package dev.chuby

import dev.chuby.data.Attendee
import dev.chuby.data.Error
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.contentType
import io.ktor.request.receive
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.*

fun Routing.attendeesApi(database: Database) {

    authenticate("myBasicAuth") {

        post("/attendees") {
            val request = call.request
            if (request.contentType() != ContentType.Application.Json) {
                throw Exception("Content-Type: ${request.contentType()} not defined for this request")
            } else {
                val attendeeRequest = call.receive<Attendee>()
                val savedAttendee = database.createAttendee(attendeeRequest)
                call.respond(HttpStatusCode.Created, savedAttendee)
            }

        }

        delete("/attendees/{id}") {
            val attendeeId = call.parameters["id"]?.toLong() ?: -1
            val deleted = database.deleteAttendee(attendeeId)
            call.respond(HttpStatusCode.Accepted, deleted)

        }
    }

    get("/attendees") {
        val request = call.request
        if (request.uri.endsWith("/"))
            throw Exception("Missing attendee id")
        else {
            val attendees = database.getAttendees()
            call.respond(attendees)
        }

    }

    get("/attendees/{id}") {
        val attendeeId = call.parameters["id"]
        val attendee = database.findAttendee(attendeeId?.toLong() ?: -1)
        call.respond(attendee)

    }

}
