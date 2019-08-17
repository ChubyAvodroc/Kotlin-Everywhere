package dev.chuby.ke_android_app

import android.util.Log
import com.google.gson.Gson
import dev.chuby.ke_android_app.model.*
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.auth.providers.basic
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

//        install(Auth) {
//            basic {
//                username = "Chuby"
//                password = "Avodroc"
//            }
//        }

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
        try {
            val response: HttpResponse = client.post("$BASE_URL/attendees") {
                header(HttpHeaders.ContentType, "application/json")
                body = attendee
            }

            when (response.status.value) {
                201 -> {
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

            when (response.status.value) {
                201 -> {
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
