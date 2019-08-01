package dev.chuby.ke_android_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chuby.ke_android_app.AttendeesRepository
import dev.chuby.ke_android_app.model.Attendee
import dev.chuby.ke_android_app.model.Loading
import dev.chuby.ke_android_app.model.Success
import dev.chuby.ke_android_app.model.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AttendeesViewModel(private val attendeesRepository: AttendeesRepository) : ViewModel(), CoroutineScope {

    companion object {
        private const val TAG = "AttendeesViewModel"
    }

    private val job = SupervisorJob()
    private val _screenState: MutableLiveData<ViewState<*>> = MutableLiveData()
    val screenState: LiveData<ViewState<*>>
        get() {
            _screenState.value = null
            return _screenState
        }

    fun addAttendee(attendee: Attendee) {
        _screenState.value = Loading
        launch {
            val savedAttendee = attendeesRepository.createAttendee(attendee)
            _screenState.value = Success(savedAttendee)
        }
    }

    fun loadAttendees() {
        _screenState.value = Loading
        launch {
            val attendees = attendeesRepository.getAttendees()
            _screenState.value = Success(attendees)
        }
    }

    fun loadAttendee(id: Long) {
        _screenState.value = Loading
        launch {
            val attendee = attendeesRepository.getAttendee(id)
            _screenState.value = Success(attendee)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}