package com.vishal.softwarejobalert.SearchByLocation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.vishal.softwarejobalert.ModelClasses.JobDetail
import com.vishal.softwarejobalert.R

class JobDetailAdapter(var context: Context) : RecyclerView.Adapter<JobDetailViewHolder>() {

    var callback1: NameClickCallback?=null

    fun setCallback(callback:NameClickCallback){
        callback1 = callback
    }
    var nameList: ArrayList<JobDetail> = ArrayList()
    fun setList(nameList1: ArrayList<JobDetail>){
        nameList = nameList1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobDetailViewHolder {

        return JobDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.job_detail_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: JobDetailViewHolder, position: Int) {

        val jobDetail: JobDetail = nameList.get(position)

       holder.title.text = jobDetail.title
       holder.company_name.text = jobDetail.companyName
       holder.location.text = jobDetail.location
       holder.date.text = jobDetail.date
       holder.detail.text = jobDetail.detail

    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}


class JobDetailViewHolder(private val view : View) : RecyclerView.ViewHolder(view){


    val title : TextView = view.findViewById(R.id.title)
    val company_name : TextView = view.findViewById(R.id.company_name)
    val location : TextView = view.findViewById(R.id.location)
    val date : TextView = view.findViewById(R.id.date)
    val detail : TextView = view.findViewById(R.id.detail)
    val checkbox:MaterialCheckBox = view.findViewById(R.id.checkbox)

}