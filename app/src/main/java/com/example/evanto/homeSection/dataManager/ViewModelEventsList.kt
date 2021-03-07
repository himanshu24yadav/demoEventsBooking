package com.example.evanto.homeSection.dataManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.evanto.helpers.UtilConstants
import com.example.evanto.homeSection.model.ModelEvents

class ViewModelEventsList : ViewModel() {

    fun fetchAllEvents(city:String? = UtilConstants.DEFAULT_CITY) {
        EventsRepository.fetchAllEvents(city)
    }

    fun getAllEvents() : LiveData<ModelEvents>? {
        return EventsRepository.masterEventsList
    }
}