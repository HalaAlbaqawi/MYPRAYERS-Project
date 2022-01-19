package com.example.prayerproject.Service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.prayerproject.R
import okhttp3.internal.notify
import java.util.*


const val CHANNELID = "my id"
const val NOTIFICATION_ID = 123
private const val TAG = "AlarmService"

class AlarmService: Service () {

//BrodcastReciver
//    override fun onReceive(context: Context?, intent: Intent?) {
//
//    }

    override fun onBind(intent: Intent?) = null



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)


        intent?.let {
            val title = intent?.getStringExtra("title")
            val text = intent?.getStringExtra("text")
            val hour = intent?.getIntExtra("hour",24)?.times(360000)
            val minute = intent?.getIntExtra("minute",60)?.times(60000)

            var timeMilli = hour + minute
            Log.d(TAG, "onStart")


            Handler().postDelayed({
                showNotification(title!!,text!!)

            }, (timeMilli - getTime()).toLong())


        }



    }

    fun getTime() : Int {
        var currenttime: Date = Calendar.getInstance().getTime()
        //milliseconds

        var hour = currenttime.hours * 3600000

        var minutes = currenttime.minutes * 60000

        val millis = hour + minutes


        return millis
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

        Log.d(TAG, "onStartCommand")



        return START_STICKY

    }


    private fun showNotification(title: String, text: String) {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }



        val notificationBuilder = NotificationCompat.Builder(this, channelId )
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setContentTitle(title)
            .setContentText(text)
            .setCategory(Notification.CATEGORY_SERVICE)
//            .setContentIntent()
            .build()
//        startForeground(101, notification)
//        notify(101, notification)
        with(NotificationManagerCompat.from(this)){
            notify(888, notification)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }




}