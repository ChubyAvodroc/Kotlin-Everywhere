package dev.chuby.ke_android_app.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.chuby.ke_android_app.R
import dev.chuby.ke_android_app.model.Attendee

class AttendeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val tvLastName: TextView = itemView.findViewById(R.id.tv_last_name)
    private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)

    fun bindTo(attendee: Attendee) {
        with(attendee) {
            tvName.text = name
            tvLastName.text = lastName
            Picasso.get().load(avatar).into(ivAvatar)
        }
    }
}