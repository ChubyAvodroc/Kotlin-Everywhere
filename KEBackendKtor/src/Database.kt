package dev.chuby

import dev.chuby.data.Attendee

interface Database {

    fun createAttendee(attendeeRequest: Attendee): Attendee

    fun getAttendees(): List<Attendee>

    fun findAttendee(id: Long): Attendee

    fun deleteAttendee(id: Long): Attendee

}
