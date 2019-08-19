package dev.chuby.ke_android_app.model

import com.google.gson.annotations.SerializedName

data class Attendee(
    val id: Long = -1,
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String = "https://api.adorable.io/avatars/250/$name.png",
    val about: String
)