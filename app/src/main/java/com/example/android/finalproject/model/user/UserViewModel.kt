package com.example.android.finalproject.model.user

import androidx.lifecycle.*
import com.example.android.finalproject.data.UserRepository
import com.example.android.finalproject.model.workout.Workout
import com.example.android.finalproject.model.workout.WorkoutRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val userData: LiveData<User> = userRepository.currentUserData.asLiveData()

    val userHistory: LiveData<List<User>> = userRepository.userHistory.asLiveData()

    val workoutHistory: LiveData<List<Workout>> = userRepository.workoutHistory.asLiveData()

    fun insert(user: User){
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }

    fun update(user: User){
        viewModelScope.launch {
            userRepository.update(user)
        }
    }

}

class UserViewModelFactory(private val userRepo: UserRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
