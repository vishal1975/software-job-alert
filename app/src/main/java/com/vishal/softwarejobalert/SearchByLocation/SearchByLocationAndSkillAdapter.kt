package com.vishal.softwarejobalert.SearchByLocation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vishal.softwarejobalert.CompanyList.ClickCallback
import com.vishal.softwarejobalert.CompanyList.CompanyModel
import com.vishal.softwarejobalert.R

class SearchByLocationAndSkillAdapter(var context:Context) : RecyclerView.Adapter<LocationAndSkillViewHolder>() {

    var callback1: NameClickCallback?=null

    fun setCallback(callback:NameClickCallback){
        callback1 = callback
    }
    var nameList: ArrayList<String> = ArrayList()
    fun setList(nameList1: ArrayList<String>){
        nameList = nameList1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAndSkillViewHolder {

        return LocationAndSkillViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.name_suggestion_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: LocationAndSkillViewHolder, position: Int) {

        val name: String = nameList.get(position)

       holder.name.text=name
        holder.name.setOnClickListener(){
 callback1?.nameClick(name);
        }

    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}


class LocationAndSkillViewHolder(private val view : View) : RecyclerView.ViewHolder(view){


    val name : TextView = view.findViewById(R.id.name)


}
interface NameClickCallback{

    fun nameClick(name: String)

}