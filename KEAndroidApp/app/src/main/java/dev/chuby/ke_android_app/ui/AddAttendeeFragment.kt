package dev.chuby.ke_android_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dev.chuby.ke_android_app.R
import dev.chuby.ke_android_app.model.Attendee
import dev.chuby.ke_android_app.model.Failure
import dev.chuby.ke_android_app.model.Loading
import dev.chuby.ke_android_app.model.Success
import dev.chuby.ke_android_app.viewmodel.AttendeeViewModelFactory
import dev.chuby.ke_android_app.viewmodel.AttendeesViewModel

class AddAttendeeFragment : Fragment() {

    companion object {
        private const val TAG = "AddAttendeeFragment"
    }

    private val viewModel by lazy {
        activity?.run {
            ViewModelProviders.of(
                this,
                AttendeeViewModelFactory()
            )[AttendeesViewModel::class.java]
        } ?: throw Exception("Invalid activity")
    }

    private lateinit var tietName: TextInputEditText
    private lateinit var tietLastName: TextInputEditText
    private lateinit var tietAbout: TextInputEditText
    private lateinit var pbLoader: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_attendee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val saveAttendee = view.findViewById<Button>(R.id.btn_save_attendee)
        tietName = view.findViewById(R.id.tiet_attendee_name)
        tietLastName = view.findViewById(R.id.tiet_attendee_last_name)
        tietAbout = view.findViewById(R.id.tiet_attendee_about)
        pbLoader = view.findViewById(R.id.pb_loader)

        viewModel.screenState.observe(this, Observer { viewState ->
            when (viewState) {
                is Loading -> showLoader()
                is Success -> {
                    hideLoader()
                    Snackbar.make(view, "Attendee added successfully", Snackbar.LENGTH_LONG).show()
                    Navigation.findNavController(view).navigateUp()
                }
                is Failure -> showFailureMessage(viewState.message)
            }

        })

        saveAttendee.setOnClickListener {
            val attendee = gatherAttendeeData()
            viewModel.addAttendee(attendee)
        }
    }

    private fun gatherAttendeeData(): Attendee {
        val name = tietName.text.toString()
        val lastName = tietLastName.text.toString()
        val about = tietAbout.text.toString()

        return Attendee(
            name = name,
            lastName = lastName,
            about = about
        )
    }

    private fun showFailureMessage(message: String) {
        hideLoader()
        Log.e(TAG, "There was an error $message")
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()

    }

    private fun showLoader() {
        pbLoader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        pbLoader.visibility = View.GONE
    }
}
