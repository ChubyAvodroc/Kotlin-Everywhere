package dev.chuby.ke_android_app.viewmodel

import androidx.lifecycle.ViewModel
import dev.chuby.ke_android_app.AttendeesRepository
import dev.chuby.ke_android_app.model.Attendee
import dev.chuby.ke_android_app.model.ViewState

class AttendeesViewModel(private val attendeesRepository: AttendeesRepository?) : ViewModel() {

    fun addAttendee(attendee: Attendee): ViewState<Nothing> {
        TODO("Implement method")
    }

    fun loadAttendees(): ViewState<List<Attendee>> {
        TODO("Implement method")
    }

    fun loadAttendee(id: Long): ViewState<Attendee> {
        TODO("Implement method")
    }
}