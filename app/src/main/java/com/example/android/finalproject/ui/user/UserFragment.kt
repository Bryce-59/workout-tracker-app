package com.example.android.finalproject.ui.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.finalproject.R
import com.example.android.finalproject.WorkoutsApplication
import com.example.android.finalproject.databinding.FragmentUserBinding
import com.example.android.finalproject.model.user.User
import com.example.android.finalproject.model.user.UserViewModel
import com.example.android.finalproject.model.user.UserViewModelFactory
import com.example.android.finalproject.model.workout.Workout
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
    private lateinit var workouts: List<Workout>

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
        userViewModel.workoutHistory.observe(viewLifecycleOwner) { history ->
            if (history.isEmpty()) {
                val builder = AlertDialog.Builder(requireContext())
                builder
                    .setMessage("No workout history found, do you want to add them?")
                    .setPositiveButton("Take me there") { _, _ ->
                        findNavController().navigate(R.id.navigation_workout)
                    }
                    .setNegativeButton("Later") {dialog, _ ->
                        dialog?.cancel()
                    }

                builder.show()
            }else {
                val calendar = Calendar.getInstance()
                val dateString = SimpleDateFormat("yyyy-MM-dd")
                    .format(calendar.time)
                userViewModel.getWorkoutToday(dateString).observe(viewLifecycleOwner){
                    workouts = it
                    println(dateString)
                    println(workouts)
                    bindWorkouts()
                }
            }
        }
        binding.bodyMeasurementCard.setOnClickListener {
            val dialog = MeasurementDialog(TYPE_UPDATE)
            dialog.show(childFragmentManager, "MeasurementDialog")
        }
    }

    private fun bindWorkouts() {
        binding.apply {
            var totalWorkoutTime = 0L
            var totalCalories = 0
            for (workout in workouts) {
                val endTime = Time(workout.endTime)
                val startTime = Time(workout.startTime)
                totalWorkoutTime += endTime.difference(startTime)
                totalCalories += workout.calories
            }
            val hours = totalWorkoutTime / 3600
            val minutes = (totalWorkoutTime - hours * 3600) / 60
            binding.workoutTimeText.text = "%02d:%02d".format(hours, minutes)
            binding.caloriesText.text = totalCalories.toString()
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

        val weightText =
            view.findViewById<EditText>(R.id.user_weight).text.toString()
        val feet = view.findViewById<EditText>(R.id.user_height_ft).text.toString()
        val inch = view.findViewById<EditText>(R.id.user_height_inch).text.toString()

        val weight = weightText.toDouble()
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

        class Time(private val timeString: String) {
            private val hour get() = timeString.split(":")[0].toInt()
            private val minute get() = timeString.split(":")[1].toInt()

            fun difference(other: Time): Int{
                val endSeconds = hour * 3600 + minute * 60
                val startSeconds = other.hour * 3600 + other.minute * 60
                return endSeconds - startSeconds
            }
        }
    }
}