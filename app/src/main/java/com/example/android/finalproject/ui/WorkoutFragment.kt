package com.example.android.finalproject.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.finalproject.*
import com.example.android.finalproject.databinding.FragmentWorkoutBinding
import com.example.android.finalproject.model.workout.Workout
import com.example.android.finalproject.model.WorkoutsApplication

class WorkoutFragment : Fragment(), WordListAdapter.OnItemClickListener {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private val newWordActivityRequestCode = 1
    private val repalceWordActivityRequestCode = 2

    private val workoutViewModel: WorkoutViewModel by viewModels {
        WordViewModelFactory((activity?.application as WorkoutsApplication).repositoryW)
    }
    private var adapter = WordListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            val intent = Intent(this@WorkoutFragment.context, NewWorkoutActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }


        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        workoutViewModel.allWords.observe(viewLifecycleOwner) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
    }

    override fun onItemClick(position: Int, view_code : Int) {
        val recyclerView = binding.recyclerview
        var viewHolder: WordListAdapter.WordViewHolder? =
            recyclerView.findViewHolderForAdapterPosition(position) as WordListAdapter.WordViewHolder;
        val curWorkout = viewHolder?.workout

        if (view_code == 0) {
            var workoutInfo = curWorkout?.let { arrayOf(it.workoutName, it.startTime, it.endTime, it.id.toString(), it.videoLink ,it.date) }
            if (workoutInfo != null) {
                Log.d("myTag", workoutInfo[0])
            };

            val intent = Intent(this@WorkoutFragment.context, NewWorkoutActivity::class.java)
            intent.putExtra(NewWorkoutActivity.SEARCH_REPLY, workoutInfo)
            startActivityForResult(intent, repalceWordActivityRequestCode)
        }else if (view_code == 1){
            val intent = Intent(this@WorkoutFragment.context, Video::class.java)
            if (curWorkout != null) {
                var link  = curWorkout.videoLink
                intent.putExtra(NewWorkoutActivity.EXTRA_REPLY, link)
            }
            startActivity(intent)
        } else if (view_code == 3){
            workoutViewModel.delete(curWorkout!!.id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        Log.d("myTag", requestCode.toString());
        Log.d("myTag", Activity.RESULT_OK.toString());
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayExtra(NewWorkoutActivity.EXTRA_REPLY)?.let { reply ->
                Log.d("myTag", reply[0]);
                val workout = Workout(0, reply[0], reply[1], reply[2], reply[4], reply[5])
                workoutViewModel.insert(workout)
            }
        }else if (requestCode == repalceWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayExtra(NewWorkoutActivity.EXTRA_REPLY)?.let { reply ->
                val workout = Workout(reply[3].toInt(), reply[0], reply[1], reply[2], reply[4], reply[5])
                workoutViewModel.update(workout)
            }
        } else {
            Toast.makeText(
                this.context,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
