package dev.chuby.ke_android_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.chuby.ke_android_app.R
import dev.chuby.ke_android_app.model.Attendee

class AttendeeAdapter(private val listener: (Attendee) -> Unit = {}) :
    ListAdapter<Attendee, AttendeeAdapter.AttendeeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendeeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return AttendeeViewHolder(itemView, listener)
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

    inner class AttendeeViewHolder(itemView: View, private val listener: (Attendee) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvLastName: TextView = itemView.findViewById(R.id.tv_last_name)
        private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)

        fun bindTo(attendee: Attendee) {
            itemView.setOnClickListener {
                listener(getItem(adapterPosition))
            }

            with(attendee) {
                tvName.text = name
                tvLastName.text = lastName
                Picasso.get().load(avatar).into(ivAvatar)
            }
        }
    }
}
