package dev.chuby

import dev.chuby.data.Attendee

object InMemoryDatabase : Database {

    private val attendees: MutableMap<Long, Attendee> = mutableMapOf(
        0L to Attendee(0L, "Adrian", "Catalán", about = "GDE"),
        1L to Attendee(1L, "Sier", "Violencia", about = "Androd dev"),
        2L to Attendee(2L, "Chuby", "Avodròc", about = "Android dev")
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


    override fun findAttendee(id: Long): Attendee {
        Thread.sleep(1500)
        val attendee: Attendee? = attendees[id]
        return attendee ?: throw AttendeeNotFoundException(
            "err_invalid_id",
            "Cannot retrieve: Attendee with id: $id was not found"
        )
    }

    override fun deleteAttendee(id: Long): Attendee {
        Thread.sleep(1500)
        val deletedAttendee: Attendee? = attendees.remove(id)
        return deletedAttendee ?: throw AttendeeNotFoundException(
            "err_invalid_id",
            "Cannot delete: Attendee with id: $id was not found"
        )
    }

}