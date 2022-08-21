package com.example.softwarejobalert.utils

import android.app.Notification
import android.app.Notification.DEFAULT_SOUND
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_SOUND
import androidx.core.content.ContextCompat.getSystemService
import com.example.softwarejobalert.MainActivity
import com.example.softwarejobalert.R
import com.google.firebase.messaging.Constants.MessageNotificationKeys.DEFAULT_SOUND
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONArray
import org.json.JSONObject

class NotifBroadcastReceiver: BroadcastReceiver() {
    val channelId = "notification_channel"
    val channelName = "com.vishal.alert"


    override fun onReceive(p0: Context?, p1: Intent?) {

//Log.v("broadcast","message arrived")
        if (p1?.getExtras() != null) {

            Log.v("broadcast","message arrived")

           var MsgBody = p1.getExtras()!!.get("gcm.notification.body").toString()
          var  MsgTitle = p1.getExtras()!!.get("gcm.notification.title").toString()
          var info=p1.getExtras()!!.get("info").toString()
            var link = p1.getExtras()!!.get("link").toString()
            var jsonObject = JSONObject()
            jsonObject.put("info",info)
            jsonObject.put("link",link)
            addNotificationData(p0!!,jsonObject)

//            for (key in p1?.getExtras()!!.keySet()) {
//                val value: Any = p1.getExtras()!!.get(key)!!
//
//                Log.e("keyChecking","key is $key and value is $value")
//
//
//
//
//
//
//            }





            }






    }

fun addNotificationData(context:Context,data1:JSONObject){


    var sh= context.getSharedPreferences("com.softwareAlet",Context.MODE_PRIVATE)

    var data = sh.getString("data","[]")

    var jsonArray = JSONArray(data.toString())

    var edit = context.getSharedPreferences("com.softwareAlet",Context.MODE_PRIVATE).edit()
    jsonArray.put(data1)
    edit.putString("data",jsonArray.toString())
    edit.apply()

    var temp =context.getSharedPreferences("com.softwareAlet",Context.MODE_PRIVATE)

    Log.v("data","${temp.getString("data","")}")
}

}