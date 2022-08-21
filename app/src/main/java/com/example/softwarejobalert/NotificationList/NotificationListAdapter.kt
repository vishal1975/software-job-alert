package com.example.softwarejobalert.NotificationList

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.softwarejobalert.CompanyList.ClickCallback
import com.example.softwarejobalert.CompanyList.CompanyModel
import com.example.softwarejobalert.NotificationDetail.NotificationDetailActivity
import com.example.softwarejobalert.R

class NotificationListAdapter(val context:Context,val notificationList: ArrayList<NotificationModel>): RecyclerView.Adapter<NotificationViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {

        return NotificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_list_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        val notification: NotificationModel = notificationList.get(position)


        Glide.with(context).load(notification.image).into(holder.companyImage)


        holder.role.text = notification.role

        holder.role_info.text = notification.role_info

        holder.layout.setOnClickListener(){
            var intent = Intent(context,NotificationDetailActivity::class.java)
            intent.putExtra("role",notification.role)
            intent.putExtra("role_info",notification.role_info)
            intent.putExtra("image",notification.image)
            intent.putExtra("apply_link",notification.apply_Link)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}


class NotificationViewHolder(private val view : View) : RecyclerView.ViewHolder(view){


    val companyImage : ImageView = view.findViewById(R.id.companyImage)
    val role : TextView = view.findViewById(R.id.role)
    val role_info : TextView = view.findViewById(R.id.role_info)
    val layout: ConstraintLayout = view.findViewById(R.id.NotificationLayout)

}