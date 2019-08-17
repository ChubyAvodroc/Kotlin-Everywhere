package dev.chuby.ke_android_app.model

sealed class Resource<out T>

data class DataResource<out T>(val data: T) : Resource<T>()

data class ErrorResource(val error: Error) : Resource<Nothing>()