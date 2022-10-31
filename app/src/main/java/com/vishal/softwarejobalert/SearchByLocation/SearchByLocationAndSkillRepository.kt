package com.vishal.softwarejobalert.SearchByLocation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import com.vishal.softwarejobalert.utils.Constants
import com.vishal.softwarejobalert.utils.WebServices


import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class SearchByLocationAndSkillRepository(var application: Application) {


    var allJobs = MutableLiveData<String>()

    fun getAllJobs( publisher:String,
                    user_ip:String,
                    keyword:String,
                    location:String,
                   limit:Int,
                   page :Int){


                 val webClient = Constants.getWebClient()
                 val services = webClient?.create(WebServices::class.java)
        Log.v("SearchByLocationAndSkil", "$keyword + $location")
      val call: Call<ResponseBody>? =services?.getAllJobs(publisher,user_ip,"Android",keyword,location,limit, page)

      call?.enqueue(object : Callback<ResponseBody> {
          override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
              val json = JSONObject(response.body()?.string())
             allJobs.value = json.toString()
              Log.v("SearchByLocationAndSkil", json.toString())
              Toast.makeText(application,json.toString(),Toast.LENGTH_LONG).show()
          }

          override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
              Toast.makeText(application,"Some Error Occured",Toast.LENGTH_LONG).show()
          }

      })





    }

}