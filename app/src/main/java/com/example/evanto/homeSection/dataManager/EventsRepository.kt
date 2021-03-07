package com.example.evanto.homeSection.dataManager

import androidx.lifecycle.MutableLiveData
import com.example.evanto.helpers.UtilConstants
import com.example.evanto.helpers.UtilMethods
import com.example.evanto.homeSection.model.ModelEvents
import com.example.evanto.retrofitManager.ApiResponseCallback
import com.example.evanto.retrofitManager.ApiServiceProvider
import com.example.evanto.retrofitManager.UrlContainer
import retrofit2.Response
import java.util.*

object EventsRepository : ApiResponseCallback {

    var masterEventsList: MutableLiveData<ModelEvents> = MutableLiveData()

    fun fetchAllEvents(city:String?){
        ApiServiceProvider().enqueueApiCall(UrlContainer.BASE_URL,this,UtilMethods.prepareFetchEventsHashMap(city))
                            .homePageData()
    }

    private fun handleFailure(msg:String?) {
        val eventsModel = ModelEvents()
        eventsModel.status = UtilConstants.STATUS_FAILURE
        eventsModel.msg = msg ?: "Some error occurred"
        masterEventsList.value = eventsModel
    }

    private fun handleSuccess(responseModelEvents: ResponseModelEvents) {
        val eventsModel = ModelEvents()

        //adding all events
        when (val linkedHashMapOfEvents = responseModelEvents.list?.masterList) {
            null -> {
                eventsModel.status = UtilConstants.STATUS_NO_DATA
                eventsModel.msg = "no data exist"
            }
            else -> {
                val models: Collection<MasterListEventModel> = linkedHashMapOfEvents.values
                val eventList = ArrayList<MasterListEventModel>(models)
                if(eventList.size > 0) {
                    eventsModel.status = UtilConstants.STATUS_SUCCESS
                    eventsModel.msg = "success"
                    eventsModel.eventData = eventList
                } else {
                    eventsModel.status = UtilConstants.STATUS_NO_DATA
                    eventsModel.msg = "no data exist"
                }
            }
        }

        //adding featured events
        responseModelEvents.featuredEventList?.let {
            eventsModel.featuredEvents = it
        }

        //adding popular events
        responseModelEvents.popularEventList?.let {
            eventsModel.popularEvents = it
        }

        masterEventsList.value = eventsModel
    }

    override fun <T> onSuccessCallback(response: Response<T>, serviceCallId: Int) {
        val responseModelEvents = response.body() as? ResponseModelEvents
        if(responseModelEvents == null) {
            handleFailure("body is empty")
        } else {
            handleSuccess(responseModelEvents)
        }
    }

    override fun onFailureCallback(errorMsg: String?, serviceCallId: Int) {
        handleFailure(errorMsg)
    }
}