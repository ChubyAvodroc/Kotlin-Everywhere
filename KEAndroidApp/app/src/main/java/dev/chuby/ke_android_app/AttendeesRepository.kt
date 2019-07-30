package dev.chuby.ke_android_app

import dev.chuby.ke_android_app.model.Attendee

interface AttendeesRepository {

    fun createAttendee(attendee: Attendee)

    fun getAttendees(): List<Attendee>

    fun getAttendee(id: Long): Attendee
}