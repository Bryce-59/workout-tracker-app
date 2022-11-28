package com.example.android.finalproject.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.finalproject.R
import com.example.android.finalproject.databinding.FragmentUserBinding
import com.example.android.finalproject.model.user.User
import com.example.android.finalproject.model.user.UserViewModel
import com.example.android.finalproject.model.user.UserViewModelFactory
import com.example.android.finalproject.model.WorkoutsApplication
import java.text.SimpleDateFormat
import java.util.*

class UserFragment : Fragment(), MeasurementDialog.MeasurementDialogListener {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((activity?.application as WorkoutsApplication).userRepository)
    }

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.userHistory.observe(viewLifecycleOwner) { user ->
            if (user.isEmpty()) {
                val dialog = MeasurementDialog()
                dialog.show(childFragmentManager, "MeasurementDialog")
            }else{
                userViewModel.userData.observe(viewLifecycleOwner) {
                    this.user = it
                    bindUser()
                }
            }
        }
    }

    private fun bindUser() {
        binding.apply {
            heightValue.text = user.height
            weightValue.text = user.weight.toString()
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, view: View) {
        val calendar = Calendar.getInstance()
        val dateString = SimpleDateFormat("yyyy-MM-dd")
            .format(calendar.time)

        val weight =
            view.findViewById<EditText>(R.id.user_weight).text.toString().toDouble()
        val feet = view.findViewById<EditText>(R.id.user_height_ft).text.toString()
        val inch = view.findViewById<EditText>(R.id.user_height_inch).text.toString()
        val user = User(
            weight = weight,
            height = feet + "\'" + inch +"\"",
            calories = 0.0,
            distance = 0.0,
            workoutTime = 0,
            date = dateString
        )
        userViewModel.insert(user)
    }
}