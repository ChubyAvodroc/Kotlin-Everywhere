package dev.chuby.ke_android_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dev.chuby.ke_android_app.R

class ActionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnAddAttendee = view.findViewById<Button>(R.id.btn_add_attendee)
        val btnShowAttendees = view.findViewById<Button>(R.id.btn_show_attendees)

        btnAddAttendee.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_actionsFragment_to_addAttendeeFragment)
        )

        btnShowAttendees.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_actionsFragment_to_attendeesFragment)
        )
    }

}
