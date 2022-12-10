package com.vishal.softwarejobalert.SearchByLocation

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vishal.softwarejobalert.ModelClasses.JobDetail
import com.vishal.softwarejobalert.utils.Constants
import com.vishal.softwarejobalert.utils.WebServices
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class JobDetailPagingSource(val publisher:String,
                            val user_ip:String,
                            val keyword:String,
                            val location:String,
                            val limit:Int
                            ) : PagingSource<Int, JobDetail>() {
    override fun getRefreshKey(state: PagingState<Int, JobDetail>): Int? {


        return state.anchorPosition?.let { anchorPosition ->
            val anchorpage = state.closestPageToPosition(anchorPosition)
             anchorpage?.prevKey?.plus(1)?:anchorpage?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JobDetail> {
        val position = params.key ?: 1

        try {
            val webClient = Constants.getWebClient()
            val services = webClient?.create(WebServices::class.java)
           val response= services?.getAllJobs(publisher,user_ip,"Android",keyword,location,limit,position)
            val data =getDataFromResponse(response?.string())
            var total = data.first
            if(data.second.size==0){
                total=position
            }

            Log.v("position","$position , $total")
           return LoadResult.Page(
                data = data.second,
               prevKey = if(position==1) null else position-1,
               nextKey = if(position==total) null else position+1
            )
        }catch (e:Exception){
            return LoadResult.Error(e)
        }
    }


    fun getDataFromResponse(response:String?) : Pair<Int,ArrayList<JobDetail>>{
        val jobDetailList = ArrayList<JobDetail>()
        val json = JSONObject(response)
        Log.v("response",json.toString())
        val total = json.getInt("total")
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {

            val detail = data.getJSONObject(i)
            val title = detail.getString("title")
            val companyName = detail.getString("company")
            val location = detail.getString("location")
            val url: String = detail.getString("url")
            val job_detail: String = detail.getString("snippet")
            val date = detail.getString("age")

            val job_date: String = parseDateToddMMyyyy(date)
            Log.v("searchByLocationAndSkil", "$title + $job_date")
            jobDetailList.add(JobDetail(title, companyName, location, job_date, url, job_detail))

        }
        return Pair(total,jobDetailList)
    }
        fun parseDateToddMMyyyy(time: String): String {

            val new_time=StringBuilder("")
            var count =0
            for(c in time){
                if(c.isWhitespace()){
                    count++
                    new_time.append(c)
                }
                if(count> 0 && (c.isDigit() || c==',')){
                    new_time.append(c)
                }
                if(count==0){
                    new_time.append(c)
                }

            }
            val inputPattern = "LLL dd, yyyy"
            val outputPattern = "dd.MM.yyyy"
            val inputFormat = SimpleDateFormat(inputPattern,Locale.getDefault())
            val outputFormat = SimpleDateFormat(outputPattern,Locale.getDefault())
            var date: Date? = null
            var str: String = ""
            try {
                date = inputFormat.parse(new_time.toString())
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }
}