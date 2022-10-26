package com.vishal.softwarejobalert.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Constants {
val baseUrl = "https://api.whatjobs.com/api/v1/jobs.json"
    var retrofit:Retrofit?=null
     fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getWebClient(): Retrofit? {
        if(retrofit!=null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }



}