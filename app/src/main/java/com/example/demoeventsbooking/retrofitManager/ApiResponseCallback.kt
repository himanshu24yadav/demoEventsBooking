package com.example.demoeventsbooking.retrofitManager

import retrofit2.Response

interface ApiResponseCallback {
    fun<T> onSuccessCallback(response: Response<T>, serviceCallId:Int)
    fun onFailureCallback(errorMsg: String?,serviceCallId:Int)
}