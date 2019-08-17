package dev.chuby.ke_android_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chuby.ke_android_app.AttendeesRepository
import dev.chuby.ke_android_app.model.*
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

    fun loadAttendees() {
        _screenState.value = Loading
        launch {
            when (val attendeesResource = attendeesRepository.getAttendees()) {
                is DataResource -> _screenState.value = Success(attendeesResource.data)
                is ErrorResource -> _screenState.value = Failure(attendeesResource.error.message)
            }
        }
    }

    fun loadAttendee(id: Long) {
        _screenState.value = Loading
        launch {
            when (val attendeeResource = attendeesRepository.getAttendee(id)) {
                is DataResource -> _screenState.value = Success(attendeeResource.data)
                is ErrorResource -> _screenState.value = Failure(attendeeResource.error.message)
            }
        }
    }

    fun addAttendee(attendee: Attendee) {
        _screenState.value = Loading
        launch {
            when (val attendeeResource = attendeesRepository.createAttendee(attendee)) {
                is DataResource -> _screenState.value = Success(attendeeResource.data)
                is ErrorResource -> _screenState.value = Failure(attendeeResource.error.message)
            }
        }
    }

    fun deleteAttendee(id: Long) {
        _screenState.value = Loading
        launch {
            when (val attendeeResource = attendeesRepository.removeAttendee(id)) {
                is DataResource -> _screenState.value = Success(attendeeResource.data)
                is ErrorResource -> _screenState.value = Failure(attendeeResource.error.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}