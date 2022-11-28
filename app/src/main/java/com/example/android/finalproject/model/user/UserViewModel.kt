package com.example.android.finalproject.model.user

import androidx.lifecycle.*
import com.example.android.finalproject.data.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    val userData: LiveData<User> = userRepository.currentUserData.asLiveData()

    val userHistory: LiveData<List<User>> = userRepository.userHistory.asLiveData()

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

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
