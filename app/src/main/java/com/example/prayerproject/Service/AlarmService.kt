package com.example.prayerproject.Service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.prayerproject.R
import com.example.prayerproject.main.MainActivity
import okhttp3.internal.notify
import java.util.*


const val CHANNELID = "my id"
const val NOTIFICATION_ID = 123
private const val TAG = "AlarmService"

class AlarmService : Service() {


    private val channelId = "Notification from Service"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
        }
    }

    override fun onBind(intent: Intent?) = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)


        intent?.let {
            val title = intent?.getStringExtra("title")
            val text = intent?.getStringExtra("text")
            val hour = intent?.getIntExtra("hour", 24)?.times(3600000)
            val minute = intent?.getIntExtra("minute", 60)?.times(60000)

            var timeMilli = hour + minute
            Log.d(TAG, "onStart")


            val currentMilliTime = getTime()

            val tMilli = (timeMilli - getTime()).toLong()
            val timer = object : CountDownTimer(tMilli, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {

                    val notificationIntent = Intent(this@AlarmService, MainActivity::class.java)
                    notificationIntent.putExtra("Dua'a",true)
                    val pendingIntent = PendingIntent.getActivity(
                        this@AlarmService,
                        0, notificationIntent, 0
                    )

                    val notification: Notification =
                        NotificationCompat.Builder(this@AlarmService, channelId)
                            .setContentTitle(title)
                            .setOngoing(true)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setPriority(PRIORITY_MIN)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .setContentText(text)
                            .setContentIntent(pendingIntent)
                            .build()
                    startForeground(1, notification)

                }
            }
            timer.start()

        }
    }

    fun getTime(): Int {
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


        val notificationBuilder = NotificationCompat.Builder(this, channelId)
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
        with(NotificationManagerCompat.from(this)) {
            notify(888, notification)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }


}