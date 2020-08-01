package com.example.demoeventsbooking.retrofitManager

import com.example.demoeventsbooking.helpers.UtilConstants.Companion.API_CONSTANT_DEFAULT
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class ApiServiceProvider (private var apiClient: OkHttpClient? = null,private var retrofit: Retrofit? = null) {

    private var apiInterface:ApiInterface? =null

    fun enqueueApiCall(baseUrl:String, apiResponseCallback: ApiResponseCallback, fieldMap:HashMap<String,String>, apiProviderConstants: Int = API_CONSTANT_DEFAULT): RetrofitCallbackManager {

        var retrofitCallbackManager: RetrofitCallbackManager

        try {

            setApiClientData(baseUrl)

            apiInterface = retrofit!!.create(ApiInterface::class.java)

            retrofitCallbackManager = RetrofitCallbackManager(apiInterface,apiResponseCallback,fieldMap,apiProviderConstants)
        }
        catch (e:Exception){
            e.printStackTrace()
            retrofitCallbackManager = RetrofitCallbackManager(apiInterface,apiResponseCallback,fieldMap,apiProviderConstants)
        }

        return retrofitCallbackManager
    }

    private fun setApiClientData(baseUrl: String) {
        when (retrofit) {
            null -> {
                if(apiClient == null) {
                    apiClient = OkHttpClientBuilder.defaultOkHttpClient
                }
                retrofit = RetrofitInstanceBuilder.getDefaultRetrofit(baseUrl,apiClient!!)
            }
        }
    }
}