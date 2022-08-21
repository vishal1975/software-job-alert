package com.example.softwarejobalert.CompanyList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwarejobalert.R

class CompanyListAdapter(var context:Context, var companyList: ArrayList<CompanyModel>):RecyclerView.Adapter<CompanyViewHolder>() {

     var callback1: ClickCallback?=null
    fun setCallback(callback: ClickCallback){
        callback1 = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {

        return CompanyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.company_subscribe_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {

        val company:CompanyModel = companyList.get(position)

        holder.companyName.text = company.companyName
        if(company.Subscribed==0){
            holder.subscribeButton.text="subscribe"
        }
        else{
            holder.subscribeButton.text="unsubscribe"
        }
        holder.subscribeButton.setOnClickListener(){


            callback1?.subscribeButtonClick(position)

        }

        holder.companyName.setOnClickListener(){
            callback1?.companyNameClick(position)
        }

    }

    override fun getItemCount(): Int {
        return companyList.size
    }
}


class CompanyViewHolder(private val view : View) : RecyclerView.ViewHolder(view){


val companyName : TextView = view.findViewById(R.id.company_name)
val subscribeButton : Button = view.findViewById(R.id.subscribeButton)

}

interface ClickCallback{

    fun subscribeButtonClick(position: Int)
    fun companyNameClick(position: Int)
}