package com.example.demoeventsbooking.retrofitManager

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.POST

interface ApiInterface {

    @POST(UrlContainer.END_URL_HOME_PAGE)
    fun homePageData(@FieldMap fieldMap: HashMap<String,String>): Call<Any?>
}