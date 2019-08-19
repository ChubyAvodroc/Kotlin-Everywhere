package dev.chuby.ke_android_app

import dev.chuby.ke_android_app.model.Attendee
import dev.chuby.ke_android_app.model.Resource

interface AttendeesRepository {

    suspend fun getAttendees(): Resource<List<Attendee>>

    suspend fun getAttendee(id: Long): Resource<Attendee>

    suspend fun createAttendee(attendee: Attendee): Resource<Attendee>

    suspend fun removeAttendee(id: Long): Resource<Attendee>
}
