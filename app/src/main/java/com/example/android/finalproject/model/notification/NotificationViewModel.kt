package com.example.android.finalproject.model.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.finalproject.model.workout.Workout
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: NotificationRepository
    private val alarmsLiveData: LiveData<List<Notification?>?>?

    init {
        alarmRepository = NotificationRepository(application)
        alarmsLiveData = alarmRepository.getAlarmsLiveData()
    }


    fun update(alarm: Notification?) = viewModelScope.launch {
        alarmRepository.update(alarm)
    }

    fun insert(alarm: Notification?) = viewModelScope.launch {
        alarmRepository.insert(alarm)
    }

    fun delete(alarm: Notification?) = viewModelScope.launch {
        if (alarm != null) {
            alarmRepository.delete(alarm.notiId)
        }
    }

    fun getAlarmsLiveData(): LiveData<List<Notification?>?>? {
        return alarmsLiveData
    }
}