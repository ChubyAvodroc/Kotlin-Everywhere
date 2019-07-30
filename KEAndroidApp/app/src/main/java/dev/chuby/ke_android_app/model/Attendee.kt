package dev.chuby.ke_android_app.model

data class Attendee(
    val name: String,
    val lastName: String,
    val avatar: String = "https://api.adorable.io/avatars/250/$name.png",
    val about: String
)