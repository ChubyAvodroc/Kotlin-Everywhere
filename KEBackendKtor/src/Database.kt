package dev.chuby

import dev.chuby.data.Attendee

object Database {

    private val attendees: MutableMap<Long, Attendee> = mutableMapOf(
        0L to Attendee(0L, "Adrian", "Catalán", about = "GDE"),
        1L to Attendee(1L, "Sier", "Violencia", about = "Androd dev"),
        2L to Attendee(2L, "Chuby", "Avodroc", about = "Android dev")
    )

    private var id: Long = attendees.size.toLong()


    fun createAttendee(attendeeRequest: Attendee): Attendee {
        Thread.sleep(1500)
        val newAttendee = with(attendeeRequest) {
            Attendee(this@Database.id, name, lastName, about = about)
        }
        attendees[id] = newAttendee
        id++

        return newAttendee
    }

    fun getAttendees(): List<Attendee> {
        Thread.sleep(1500)
        return attendees.values.toList()
    }


    fun findAttendee(id: Long): Attendee? {
        Thread.sleep(1500)
        return attendees[id]
    }

    fun deleteAttendee(id: Long): Boolean {
        Thread.sleep(1500)
        return attendees.remove(id) != null
    }

}