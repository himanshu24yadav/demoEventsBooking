package com.example.evanto.helpers

class UtilMethods {
    companion object {
        fun prepareFetchEventsHashMap(city:String?) : HashMap<String,String> {
            val hashMap = HashMap<String,String>()
            hashMap[UtilConstants.PARAM_NORM] = "1"
            hashMap[UtilConstants.PARAM_FILTER_BY] = "go-out"
            hashMap[UtilConstants.PARAM_CITY] = city ?: UtilConstants.DEFAULT_CITY
            return hashMap
        }
    }
}