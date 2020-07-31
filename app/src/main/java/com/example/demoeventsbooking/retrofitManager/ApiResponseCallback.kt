package com.example.demoeventsbooking.retrofitManager

import retrofit2.Response

interface ApiResponseCallback {
    fun onSuccessCallback(response: Response<Any?>, serviceCallId:Int)
    fun onFailureCallback(throwable: Throwable,serviceCallId:Int)
}