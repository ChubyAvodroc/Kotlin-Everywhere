package dev.chuby.ke_android_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.chuby.ke_android_app.R
import dev.chuby.ke_android_app.model.Attendee

class AttendeeAdapter : ListAdapter<Attendee, AttendeeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendeeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return AttendeeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AttendeeViewHolder, position: Int) {
        val attendee = getItem(position)
        holder.bindTo(attendee)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_attendee
    }

    class DiffCallback : DiffUtil.ItemCallback<Attendee>() {

        override fun areItemsTheSame(oldItem: Attendee, newItem: Attendee) = false

        override fun areContentsTheSame(oldItem: Attendee, newItem: Attendee) = false
    }
}
