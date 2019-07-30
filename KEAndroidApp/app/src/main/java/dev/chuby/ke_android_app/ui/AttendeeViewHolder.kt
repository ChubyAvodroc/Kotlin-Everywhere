package dev.chuby.ke_android_app.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.chuby.ke_android_app.R
import dev.chuby.ke_android_app.model.Attendee

class AttendeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val tvLastName: TextView = itemView.findViewById(R.id.tv_last_name)

    fun bindTo(attendee: Attendee) {
        with(attendee) {
            tvName.text = name
            tvLastName.text = lastName
        }
    }
}