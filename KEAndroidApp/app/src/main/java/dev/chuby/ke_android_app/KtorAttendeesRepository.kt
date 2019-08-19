package dev.chuby.ke_android_app

import android.util.Log
import com.google.gson.Gson
import dev.chuby.ke_android_app.model.*
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
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
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readBytes
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.charset.Charset

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
        try {
            val response: HttpResponse = client.get("$BASE_URL/attendees")

            when (response.status) {
                HttpStatusCode.OK -> {
                    val attendees: List<Attendee> = response.receive()
                    DataResource(attendees)
                }
                else -> {
                    val bytes = response.readBytes()
                    val error2 = bytes.toString(Charset.defaultCharset())

                    ErrorResource(Gson().fromJson(error2, Error::class.java))
                }
            }

        } catch (cause: Throwable) {
            Log.e(TAG, "There was an error in the request: ", cause)
            throw cause
        }
    }

    override suspend fun getAttendee(id: Long): Resource<Attendee> = withContext(Dispatchers.IO) {
        try {
            val response: HttpResponse = client.get("$BASE_URL/attendees/$id")

            when (response.status) {
                HttpStatusCode.OK -> {
                    val attendee: Attendee = response.receive()
                    DataResource(attendee)
                }
                else -> {
                    val bytes = response.readBytes()
                    val error2 = bytes.toString(Charset.defaultCharset())

                    ErrorResource(Gson().fromJson(error2, Error::class.java))
                }
            }
        } catch (cause: Throwable) {
            Log.e(TAG, "There was an error in the request: ", cause)
            throw cause
        }
    }

    override suspend fun createAttendee(attendee: Attendee): Resource<Attendee> = withContext(Dispatchers.IO) {
        try {
            val response: HttpResponse = client.post("$BASE_URL/attendees") {
                header(HttpHeaders.ContentType, "application/json")
                header(HttpHeaders.Authorization, "Basic Q2h1Ynk6QXZvZHJvYw==")
                body = attendee
            }

            when (response.status) {
                HttpStatusCode.Created -> {
                    val savedAttendee: Attendee = response.receive()
                    DataResource(savedAttendee)
                }
                else -> {
                    val bytes = response.readBytes()
                    val error2 = bytes.toString(Charset.defaultCharset())

                    ErrorResource(Gson().fromJson(error2, Error::class.java))
                }
            }
        } catch (cause: Throwable) {
            Log.e(TAG, "There was an error in the request: ", cause)
            throw cause
        }
    }

    override suspend fun removeAttendee(id: Long): Resource<Attendee> = withContext(Dispatchers.IO) {
        try {
            val response: HttpResponse = client.delete("$BASE_URL/attendees/$id") {
                header(HttpHeaders.ContentType, "application/json")
                header(HttpHeaders.Authorization, "Basic Q2h1Ynk6QXZvZHJvYw==")
            }

            when (response.status) {
                HttpStatusCode.Accepted -> {
                    val savedAttendee: Attendee = response.receive()
                    DataResource(savedAttendee)
                }
                else -> {
                    val bytes = response.readBytes()
                    val error2 = bytes.toString(Charset.defaultCharset())

                    ErrorResource(Gson().fromJson(error2, Error::class.java))
                }
            }
        } catch (cause: Throwable) {
            Log.e(TAG, "There was an error in the request: ", cause)
            throw cause
        }
    }
}
