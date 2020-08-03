package com.example.demoeventsbooking.homeSection.model

import com.example.demoeventsbooking.homeSection.dataManager.MasterListEventModel

class ModelEvents (
    var eventData : ArrayList<MasterListEventModel> = ArrayList(),
    var featuredEvents : ArrayList<MasterListEventModel> = ArrayList(),
    var popularEvents : ArrayList<MasterListEventModel> = ArrayList(),
    var status : String? = null,
    var msg:String? = null
)