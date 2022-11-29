package com.vishal.softwarejobalert.utils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v1/jobs.json")
   suspend fun getAllJobs(
@Query("publisher") publisher:String,
@Query("user_ip") user_ip:String,
@Query("user_agent") user_agent:String,
@Query("keyword") keyword:String,
@Query("location") location:String,
@Query("limit") limit:Int,
@Query("page") page :Int
    ): ResponseBody
}