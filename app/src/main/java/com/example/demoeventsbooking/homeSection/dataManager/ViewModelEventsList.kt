package com.example.demoeventsbooking.homeSection.dataManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ViewModelEventsList : ViewModel() {

    fun fetchAllEvents(city:String? = "mumbai") {
        EventsRepository.fetchAllEvents(city)
    }

    fun getAllEvents() : LiveData<ArrayList<MasterListEventModel>?>? {
        return EventsRepository.masterEventList
    }
}