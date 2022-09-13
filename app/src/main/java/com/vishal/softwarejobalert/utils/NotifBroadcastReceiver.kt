package com.vishal.softwarejobalert.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
          var role=p1.getExtras()!!.get("role").toString()
            var role_info = p1.getExtras()!!.get("role_info").toString()

            var image = p1.getExtras()!!.get("image").toString()
            var apply_link = p1.getExtras()!!.get("apply_link").toString()

            var jsonObject = JSONObject()
            jsonObject.put("role",role)
            jsonObject.put("role_info",role_info)
            jsonObject.put("image",image)
            jsonObject.put("apply_link",apply_link)
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