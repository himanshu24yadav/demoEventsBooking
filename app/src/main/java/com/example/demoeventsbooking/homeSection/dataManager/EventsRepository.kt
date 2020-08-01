package com.example.demoeventsbooking.homeSection.dataManager

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.demoeventsbooking.CustomApplicationClass
import com.example.demoeventsbooking.helpers.UtilMethods
import com.example.demoeventsbooking.retrofitManager.ApiResponseCallback
import com.example.demoeventsbooking.retrofitManager.ApiServiceProvider
import com.example.demoeventsbooking.retrofitManager.UrlContainer
import retrofit2.Response

object EventsRepository : ApiResponseCallback {

    private val mContext: Context? = CustomApplicationClass.applicationContext()
    var masterEventList: MutableLiveData<ArrayList<MasterListEventModel>?> = MutableLiveData()

    fun fetchAllEvents(city:String?){
        ApiServiceProvider().enqueueApiCall(UrlContainer.BASE_URL,this,UtilMethods.prepareFetchEventsHashMap(city)).homePageData()
    }

    override fun <T> onSuccessCallback(response: Response<T>, serviceCallId: Int) {
        UtilMethods.showToast(mContext,"onSuccess")
    }

    override fun onFailureCallback(errorMsg: String?, serviceCallId: Int) {
        UtilMethods.showToast(mContext,"onFailure")
    }
}