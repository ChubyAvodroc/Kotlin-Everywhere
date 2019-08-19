package dev.chuby.ke_android_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.chuby.ke_android_app.R
import dev.chuby.ke_android_app.model.Attendee
import dev.chuby.ke_android_app.model.Failure
import dev.chuby.ke_android_app.model.Loading
import dev.chuby.ke_android_app.model.Success
import dev.chuby.ke_android_app.viewmodel.AttendeeViewModelFactory
import dev.chuby.ke_android_app.viewmodel.AttendeesViewModel

class AttendeesFragment : Fragment() {

    companion object {
        private const val TAG = "AttendeesFragment"
    }

    private val viewModel by lazy {
        activity?.run {
            ViewModelProviders.of(
                this,
                AttendeeViewModelFactory()
            )[AttendeesViewModel::class.java]
        } ?: throw Exception("Invalid activity")
    }

    private lateinit var rvAttendees: RecyclerView
    private lateinit var pbLoader: ProgressBar
    private val adapter = AttendeeAdapter { selectedAttendee ->
        Log.i(TAG, "Selected attendee: $selectedAttendee")
        val arguments = Bundle().apply {
            putLong(AttendeeFragment.ATTENDEE_ID, selectedAttendee.id)
        }
        Navigation.findNavController(view!!).navigate(
            R.id.action_attendeesFragment_to_attendeeFragment,
            arguments
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_attendees, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAttendees = view.findViewById(R.id.rv_attendees)
        pbLoader = view.findViewById(R.id.pb_loader)

        rvAttendees.adapter = adapter

        viewModel.screenState.observe(this, Observer { viewState ->
            when (viewState) {
                is Loading -> showLoader()
                is Success -> showAttendees(viewState.data as List<Attendee>)
                is Failure -> showFailureMessage(viewState.message)
            }
        })

        viewModel.loadAttendees()

    }

    private fun showAttendees(attendees: List<Attendee>) {
        hideLoader()
        adapter.submitList(attendees)
    }

    private fun showFailureMessage(message: String) {
        hideLoader()
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoader() {
        pbLoader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        pbLoader.visibility = View.GONE
    }

}
