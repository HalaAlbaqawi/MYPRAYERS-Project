package com.example.prayerproject.Service

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.prayerproject.R
import com.example.prayerproject.main.MainActivity
import com.example.prayerproject.view.PrayersTimeViewModel


const val CHANNEL_ID = "your id"
const val ADHAN_NOTIFICATION_ID = 123

class MyService : Service(){
    private lateinit var adhanPlayer: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null    }

    override fun onCreate() {
        super.onCreate()

        initAdhan()
        creareNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        showNotification()



        if (adhanPlayer.isPlaying) {
            adhanPlayer.start()
        }else{
            adhanPlayer.stop()

        }

        return START_STICKY
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(){

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0)

        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("Adhan Player")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(ADHAN_NOTIFICATION_ID,notification)

    }


    private fun creareNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChanel = NotificationChannel(
                CHANNEL_ID, "My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChanel)
        }


    }

    private fun initAdhan(){

        adhanPlayer = MediaPlayer.create(this, R.raw.adhan)
        adhanPlayer.isLooping = false
        adhanPlayer.setVolume(100f,100f)
        adhanPlayer.start()
    }



}