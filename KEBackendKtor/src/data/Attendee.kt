package dev.chuby.data

import com.google.gson.annotations.SerializedName

data class Attendee(
    val id: Long,
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String = "https://api.adorable.io/avatars/250/$name.png",
    val about: String
)