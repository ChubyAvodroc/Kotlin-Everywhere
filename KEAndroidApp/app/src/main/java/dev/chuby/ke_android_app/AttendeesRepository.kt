package dev.chuby.ke_android_app

import dev.chuby.ke_android_app.model.Attendee

interface AttendeesRepository {

    suspend fun createAttendee(attendee: Attendee): Attendee

    suspend fun getAttendees(): List<Attendee>

    suspend fun getAttendee(id: Long): Attendee
}