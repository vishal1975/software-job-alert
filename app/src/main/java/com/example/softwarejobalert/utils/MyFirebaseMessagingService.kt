package com.example.softwarejobalert.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.softwarejobalert.MainActivity
import com.example.softwarejobalert.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Exception

class MyFirebaseMessagingService:FirebaseMessagingService() {
    val channelId = "notification_channel"
    val channelName = "com.vishal.alert"

    override fun onMessageReceived(message: RemoteMessage) {

        generateNotification(applicationContext,message.notification!!.title.toString(),message.notification!!.body.toString())
    }

    fun generateNotification(context: Context, title: String, message: String){

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notificaiton_colored)
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                //   .setOnlyAlertOnce(true)
                //  .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

        }


        notificationManager.notify(1, builder.build())






    }

    override fun onSendError(msgId: String, exception: Exception) {
        Toast.makeText(applicationContext,"${exception.message}",Toast.LENGTH_LONG).show()
    }
}
