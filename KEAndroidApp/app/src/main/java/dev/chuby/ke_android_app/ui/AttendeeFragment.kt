package dev.chuby.ke_android_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dev.chuby.ke_android_app.R
import dev.chuby.ke_android_app.model.*
import dev.chuby.ke_android_app.viewmodel.AttendeeViewModelFactory
import dev.chuby.ke_android_app.viewmodel.AttendeesViewModel

class AttendeeFragment : Fragment() {

    companion object {
        private const val TAG = "AttendeeFragment"
        const val ATTENDEE_ID = "attendee_id"
    }

    private val viewModel by lazy {
        activity?.run {
            ViewModelProviders.of(
                this,
                AttendeeViewModelFactory()
            )[AttendeesViewModel::class.java]
        } ?: throw Exception("Invalid activity")
    }

    private lateinit var ivAvatar: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvLastName: TextView
    private lateinit var tvAbout: TextView
    private lateinit var pbLoader: ProgressBar
    private var attendeeId: Long = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attendeeId = arguments?.getLong(ATTENDEE_ID) ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_attendee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivAvatar = view.findViewById(R.id.iv_avatar)
        tvName = view.findViewById(R.id.tv_attendee_name)
        tvLastName = view.findViewById(R.id.tv_attendee_last_name)
        tvAbout = view.findViewById(R.id.tv_attendee_about)
        pbLoader = view.findViewById(R.id.pb_loader)
        val deleteAttendee: Button = view.findViewById(R.id.btn_delete_attendee)

        deleteAttendee.setOnClickListener {
            viewModel.deleteAttendee(attendeeId)
        }

        viewModel.screenState.observe(this, Observer { viewState ->
            when (viewState) {
                is Loading -> showLoader()
                is Deleted -> {
                    hideLoader()
                    Snackbar.make(view, "Attendee deleted successfully", Snackbar.LENGTH_LONG).show()
                    Navigation.findNavController(view).navigateUp()
                }
                is Success -> {
                    hideLoader()
                    bindData(viewState.data as Attendee)
                }
                is Failure -> showFailureMessage(viewState.message)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAttendee(attendeeId)
    }

    private fun bindData(attendee: Attendee) {
        with(attendee) {
            Picasso.get().load(avatar).into(ivAvatar)
            tvName.text = name
            tvLastName.text = lastName
            tvAbout.text = about
        }
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
