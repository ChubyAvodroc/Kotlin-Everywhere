package dev.chuby.ke_android_app.model

sealed class ViewState<out T>

data class Success<out T>(val data: T) : ViewState<T>()

data class Failure(val message: String) : ViewState<Nothing>()

object Loading : ViewState<Nothing>()
