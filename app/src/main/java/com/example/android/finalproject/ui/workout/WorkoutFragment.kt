package com.example.android.finalproject.ui.workout

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.finalproject.R
import com.example.android.finalproject.WordListAdapter
import com.example.android.finalproject.WorkoutViewModel
import com.example.android.finalproject.WordViewModelFactory
import com.example.android.finalproject.WorkoutsApplication
import com.example.android.finalproject.databinding.FragmentWorkoutBinding
import com.example.android.finalproject.model.workout.Workout
import com.example.android.finalproject.ui.notification.Video

class WorkoutFragment : Fragment(), WordListAdapter.OnItemClickListener {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel: WorkoutViewModel by viewModels {
        WordViewModelFactory(
            (activity?.application as WorkoutsApplication).repositoryW
        )
    }

    private val adapter = WordListAdapter(this)

    private val newWorkoutLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data
                    ?.getStringArrayExtra(NewWorkoutActivity.EXTRA_REPLY)
                    ?.let { reply ->
                        val workout = Workout(
                            0,
                            reply[0],
                            reply[1],
                            reply[2],
                            reply[4],
                            reply[5],
                            reply[6].toInt()
                        )

                        workoutViewModel.insert(workout)

                        val mediaPlayer = MediaPlayer.create(
                            requireContext(),
                            R.raw.tada
                        )
                        mediaPlayer.start()
                    }
            } else {
                showNotSavedMessage()
            }
        }

    private val replaceWorkoutLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data
                    ?.getStringArrayExtra(NewWorkoutActivity.EXTRA_REPLY)
                    ?.let { reply ->
                        val workout = Workout(
                            reply[3].toInt(),
                            reply[0],
                            reply[1],
                            reply[2],
                            reply[4],
                            reply[5],
                            reply[6].toInt()
                        )

                        workoutViewModel.update(workout)
                    }
            } else {
                showNotSavedMessage()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            val intent = Intent(
                requireContext(),
                NewWorkoutActivity::class.java
            )
            newWorkoutLauncher.launch(intent)
        }

        binding.recyclerview.apply {
            adapter = this@WorkoutFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        workoutViewModel.allWords.observe(viewLifecycleOwner) { words ->
            adapter.submitList(words)
        }
    }

    override fun onItemClick(position: Int, view_code: Int) {
        val viewHolder =
            binding.recyclerview.findViewHolderForAdapterPosition(position)
                    as? WordListAdapter.WordViewHolder

        val curWorkout = viewHolder?.workout

        when (view_code) {
            0 -> {
                val workoutInfo = curWorkout?.let {
                    arrayOf(
                        it.workoutName,
                        it.startTime,
                        it.endTime,
                        it.id.toString(),
                        it.videoLink,
                        it.date,
                        it.calories.toString()
                    )
                }

                val intent = Intent(
                    requireContext(),
                    NewWorkoutActivity::class.java
                )

                intent.putExtra(
                    NewWorkoutActivity.SEARCH_REPLY,
                    workoutInfo
                )

                replaceWorkoutLauncher.launch(intent)
            }

            1 -> {
                curWorkout?.let {
                    val intent = Intent(
                        requireContext(),
                        Video::class.java
                    )

                    intent.putExtra(
                        NewWorkoutActivity.EXTRA_REPLY,
                        it.videoLink
                    )

                    startActivity(intent)
                }
            }

            3 -> {
                curWorkout?.let {
                    workoutViewModel.delete(it.id)
                }
            }
        }
    }

    private fun showNotSavedMessage() {
        Toast.makeText(
            requireContext(),
            R.string.empty_not_saved,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}