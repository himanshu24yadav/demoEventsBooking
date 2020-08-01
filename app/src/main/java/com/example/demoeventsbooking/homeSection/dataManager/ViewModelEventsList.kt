package com.example.demoeventsbooking.homeSection.dataManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.demoeventsbooking.helpers.UtilConstants
import com.example.demoeventsbooking.homeSection.model.ModelEvents

class ViewModelEventsList : ViewModel() {

    fun fetchAllEvents(city:String? = UtilConstants.DEFAULT_CITY) {
        EventsRepository.fetchAllEvents(city)
    }

    fun getAllEvents() : LiveData<ModelEvents>? {
        return EventsRepository.masterEventsList
    }
}