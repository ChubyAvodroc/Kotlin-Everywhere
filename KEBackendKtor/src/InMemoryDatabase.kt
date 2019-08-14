package dev.chuby

import dev.chuby.data.Attendee

object InMemoryDatabase : Database {

    private val attendees: MutableMap<Long, Attendee> = mutableMapOf(
        0L to Attendee(0L, "Adrian", "Catalán", about = "GDE"),
        1L to Attendee(1L, "Sier", "Violencia", about = "Androd dev"),
        2L to Attendee(2L, "Chuby", "Avodroc", about = "Android dev")
    )

    private var id: Long = attendees.size.toLong()


    override fun createAttendee(attendeeRequest: Attendee): Attendee {
        Thread.sleep(1500)
        val newAttendee = with(attendeeRequest) {
            Attendee(this@InMemoryDatabase.id, name, lastName, about = about)
        }
        attendees[id] = newAttendee
        id++

        return newAttendee
    }

    override fun getAttendees(): List<Attendee> {
        Thread.sleep(1500)
        return attendees.values.toList()
    }


    override fun findAttendee(id: Long): Attendee? {
        Thread.sleep(1500)
        return attendees[id]
    }

    override fun deleteAttendee(id: Long): Boolean {
        Thread.sleep(1500)
        return attendees.remove(id) != null
    }

}