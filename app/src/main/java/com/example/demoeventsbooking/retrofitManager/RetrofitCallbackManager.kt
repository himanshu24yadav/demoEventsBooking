package com.example.demoeventsbooking.retrofitManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitCallbackManager (private var apiInterface: ApiInterface?, private var apiResponseCallback: ApiResponseCallback?, private var fieldMap: HashMap<String, String>?, private var apiProviderConstants: Int) {

    private fun sendCallbacks(call:Call<Any?>){
        call.enqueue(object : Callback<Any?>{
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                apiResponseCallback?.onSuccessCallback(response, apiProviderConstants)
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                apiResponseCallback?.onFailureCallback(t,apiProviderConstants)
            }
        })
    }



    //add all apiCallMethods
    fun homePageData(){
        val call = apiInterface?.homePageData(fieldMap!!)
        call?.let { sendCallbacks(it) }
    }
}