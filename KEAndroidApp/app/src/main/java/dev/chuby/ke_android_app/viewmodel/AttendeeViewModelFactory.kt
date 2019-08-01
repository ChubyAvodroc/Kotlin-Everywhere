package dev.chuby.ke_android_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.chuby.ke_android_app.AttendeesRepository
import dev.chuby.ke_android_app.KtorAttendeesRepository

class AttendeeViewModelFactory(private val attendeesRepository: AttendeesRepository = KtorAttendeesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AttendeesViewModel::class.java))
            AttendeesViewModel(attendeesRepository) as T
        else
            throw IllegalArgumentException("ViewModel not found")
    }

}