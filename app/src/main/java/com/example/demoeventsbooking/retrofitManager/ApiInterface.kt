package com.example.demoeventsbooking.retrofitManager

import com.example.demoeventsbooking.homeSection.dataManager.ResponseModelEvents
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {
    @GET(UrlContainer.END_URL_HOME_PAGE)
    fun homePageData(@QueryMap fieldMap: HashMap<String,String>): Call<ResponseModelEvents?>
}