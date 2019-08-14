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
                call.respond(
                    HttpStatusCode.BadRequest,
                    Error("bad ContentType", "${request.contentType()} not defined for this request")
                )
            } else {
                val attendeeRequest = call.receive<Attendee>()
                val savedAttendee = database.createAttendee(attendeeRequest)
                call.respond(HttpStatusCode.Created, savedAttendee)
            }

        }
    }

    get("/attendees") {
        val request = call.request
        if (request.uri.endsWith("/"))
            call.respond(
                HttpStatusCode.NotFound,
                Error("bad endpoint", "Missing attendee id")
            )
        else {
            val attendees = database.getAttendees()
            call.respond(attendees)
        }

    }

    get("/attendees/{id}") {
        val attendeeId = call.parameters["id"]
        println("Param: $attendeeId")
        val attendee = database.findAttendee(attendeeId?.toLong() ?: -1)

        if (attendee != null)
            call.respond(attendee)
        else
            call.respond(
                HttpStatusCode.NotFound,
                Error("Id: $attendeeId in invalid", "Cannot retrieve: Attendee not found")
            )

    }

    authenticate("myBasicAuth") {

        delete("/attendees/{id}") {
            val attendeeId = call.parameters["id"]?.toLong() ?: -1
            println("Param: $attendeeId")

            val attendee = database.findAttendee(attendeeId)

            val deleted = database.deleteAttendee(attendeeId)

            if (deleted)
                call.respond(HttpStatusCode.OK, attendee as Attendee)
            else
                call.respond(
                    HttpStatusCode.NotFound,
                    Error("Id: $attendeeId in invalid", "Cannot delete: Attendee not found")
                )

        }
    }

}
