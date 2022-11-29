package com.vishal.softwarejobalert.SearchByLocation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vishal.softwarejobalert.ModelClasses.JobDetail
import kotlinx.coroutines.flow.Flow


class SearchByLocationAndSkillViewModel(application:Application) : AndroidViewModel(application) {

    val repository = SearchByLocationAndSkillRepository(application)
    fun getAllJobs(publisher:String,
                   user_ip:String,
                   keyword:String,
                   location:String,
                   limit:Int): Flow<PagingData<JobDetail>> {

     return   Pager(
            config = PagingConfig(pageSize = limit, enablePlaceholders = false),
            pagingSourceFactory = { repository.getAllJobs(publisher, user_ip, keyword, location, limit) }
        )
            .flow
    }

}