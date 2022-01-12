package com.example.prayerproject.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import com.example.prayerproject.R
import android.widget.Toast
import com.example.prayerproject.main.handler
import androidx.core.app.NotificationManagerCompat
import java.util.*


const val CHANNELID = "my id"
const val NOTIFICATION_ID = 123
private const val TAG = "AlarmService"
class AlarmService: Service() {


    override fun onBind(intent: Intent?) = null



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)


        val title = intent?.getStringExtra("title")
        val text = intent?.getStringExtra("text")
        val hour =intent?.getIntExtra("hour",24)
        val minute =intent?.getIntExtra("minute",60)

        Log.d(TAG, "onStart")


        Handler().postDelayed({
              showNotification(title!!,text!!)

        },10000)


    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

        Log.d(TAG, "onStartCommand")


//
//        Handler().postDelayed({
//            Toast.makeText(this, "Service is still running", Toast.LENGTH_LONG).show()
//            //  showNotification("title!!,text!!","DSfsdfsd")
//
//           // notification()
//
//        },8000)

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
            .build()
        startForeground(101, notification)
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
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun showNotification(title: String, text: String){
//
//        val notificationIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0)
//
//        val notification = Notification
//            .Builder(this, CHANNELID)
////            .setChannelId("")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(title)
//            .setContentText(text)
//            .build()
//
//        startForeground(NOTIFICATION_ID,notification)
//
//    }


    fun time() {

            val time = Calendar.getInstance()
            println(time.timeInMillis)


    }

}