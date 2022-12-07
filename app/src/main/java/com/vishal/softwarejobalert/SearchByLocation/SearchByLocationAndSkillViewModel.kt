package com.vishal.softwarejobalert.SearchByLocation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vishal.softwarejobalert.ModelClasses.JobDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchByLocationAndSkillViewModel @Inject constructor(val repository:SearchByLocationAndSkillRepository): ViewModel() {

//    val repository = SearchByLocationAndSkillRepository()
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