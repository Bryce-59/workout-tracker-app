package com.example.android.finalproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import kotlin.math.pow

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
                val dialog = MeasurementDialog(TYPE_CREATE)
                dialog.show(childFragmentManager, "MeasurementDialog")
            }else{
                userViewModel.userData.observe(viewLifecycleOwner) {
                    this.user = it
                    bindUser()
                }
            }
        }
        binding.bodyMeasurementCard.setOnClickListener {
            val dialog = MeasurementDialog(TYPE_UPDATE)
            dialog.show(childFragmentManager, "MeasurementDialog")
        }
    }

    private fun bindUser() {
        binding.apply {
            heightValue.text = user.height
            weightValue.text = user.weight.toString()
            lastUpdateText.text = "last updated " + user.date

            val heightText = user.height.split("\'")
            val bmi =
                user.weight / (heightText[0].toDouble() * 12 + heightText[1].dropLast(1)
                    .toInt()).pow(2.0) * 703
            bmiText.text = String.format("%.2f (%s)", bmi, bmiCategory(bmi))
        }
    }

    private fun bmiCategory(bmi: Double): String{
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Normal"
            bmi in 25.0..29.9 -> "Overweight"
            bmi in 30.0..34.9 -> "Obese"
            bmi in 35.0..39.9 -> "Severely Obese"
            else -> "Morbidly Obese"
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, view: View, type: Int) {
        val calendar = Calendar.getInstance()
        val dateString = SimpleDateFormat("yyyy-MM-dd")
            .format(calendar.time)

        val weight =
            view.findViewById<EditText>(R.id.user_weight).text.toString().toDouble()
        val feet = view.findViewById<EditText>(R.id.user_height_ft).text.toString()
        val inch = view.findViewById<EditText>(R.id.user_height_inch).text.toString()


        if (type == TYPE_CREATE) {
            val user = User(
                weight = weight,
                height = feet + "\'" + inch +"\"",
                date = dateString
            )
            userViewModel.insert(user)
        } else if(type == TYPE_UPDATE){
            val user = User(
                id = user.id,
                weight = weight,
                height = feet + "\'" + inch +"\"",
                date = dateString
            )
            userViewModel.update(user)
        }

    }

    companion object{
        const val TYPE_CREATE = 1
        const val TYPE_UPDATE = 2
    }
}