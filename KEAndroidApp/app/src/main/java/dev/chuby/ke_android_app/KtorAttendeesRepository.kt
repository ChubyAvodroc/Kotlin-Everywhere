package dev.chuby.ke_android_app

import dev.chuby.ke_android_app.model.Attendee
import dev.chuby.ke_android_app.model.DataResource
import dev.chuby.ke_android_app.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object KtorAttendeesRepository : AttendeesRepository {

    private const val TAG = "KtorAttendeesRepository"
    private const val BASE_URL = "http://192.168.1.129:8090"

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer {
                serializeNulls()
                disableHtmlEscaping()
            }
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

    }

    override suspend fun getAttendees(): Resource<List<Attendee>> = withContext(Dispatchers.IO) {
        val attendees = client.get<List<Attendee>>("$BASE_URL/attendees")
        DataResource(attendees)
    }

    override suspend fun getAttendee(id: Long): Resource<Attendee> = withContext(Dispatchers.IO) {
        val attendee = client.get<Attendee>("$BASE_URL/attendees/$id")
        DataResource(attendee)
    }

    override suspend fun createAttendee(attendee: Attendee): Resource<Attendee> = withContext(Dispatchers.IO) {
        val savedAttendee = client.post<Attendee>("$BASE_URL/attendees") {
            header(HttpHeaders.ContentType, "application/json")
            body = attendee
        }
        DataResource(savedAttendee)
    }

    override suspend fun removeAttendee(id: Long): Resource<Attendee> = withContext(Dispatchers.IO) {
        val deletedAttendee = client.delete<Attendee>("$BASE_URL/attendees/$id") {
            header(HttpHeaders.ContentType, "aaplication/json")
        }
        DataResource(deletedAttendee)
    }
}