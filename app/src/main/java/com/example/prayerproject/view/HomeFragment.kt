package com.example.prayerproject.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.R
import com.example.prayerproject.databinding.FragmentHomeBinding
import com.example.prayerproject.main.LoginActivity
import java.time.LocalDate
import java.time.LocalTime
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.util.*

private lateinit var binding: FragmentHomeBinding
private const val TAG = "HomeFragment"

lateinit var notificationChannel: NotificationChannel
private val notification_id = "notification"
lateinit var builder: Notification.Builder
private val notificationDescription = "notification"
lateinit var notificationManager: NotificationManager

class HomeFragment : Fragment() {

    private lateinit var logout: MenuItem

    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // to connect the action bar with menu
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)
        logout = menu.findItem(R.id.logout_item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val formatter = DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now())
        binding.timeTextView.text = formatter.toString()

        notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        time()


        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        binding.gregorianDateTextView.text = currentDate

        // hijri date format

        val dt: LocalDate = LocalDate.now()

        val hijrahDate: HijrahDate = HijrahDate.from(dt)

        val formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val formatted = formatter1.format(hijrahDate)

        Log.d(TAG, formatted)
        binding.hijriDateTextView.text = formatted

        val cal = Calendar.getInstance()
        val monthDate = SimpleDateFormat("MMMM")
        val monthName = monthDate.format(cal.time)
        binding.gregorianMonthTextView.text = monthName
        Log.d(TAG, monthName)


        val formatter2 = DateTimeFormatter.ofPattern("MMMM")
        val formatted1 = formatter2.format(hijrahDate)
        binding.hijriMonthTextView.text = formatted1
        Log.d(TAG, formatted1)

    }

    fun notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                notification_id,
                notificationDescription,
                NotificationManager.IMPORTANCE_HIGH
            )



            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(requireActivity(), notification_id)
                .setSmallIcon(R.drawable.p)
                .setContentTitle("oh my Allah  make me one who establishes regular Prayer, and also (raise such) among my offspring O our Allah and accept Thou my Prayer.")
                .setContentText("رَبِّ ٱجْعَلْنِى مُقِيمَ ٱلصَّلَوٰةِ وَمِن ذُرِّيَّتِى ۚ رَبَّنَا وَتَقَبَّلْ دُعَآءِ")
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.myprayers))

        } else {
            builder = Notification.Builder(requireActivity())
                .setSmallIcon(R.drawable.myprayers)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.myprayers))
        }
        notificationManager.notify(1234, builder.build())
    }

    // to set the timer for notification
    fun time() {
        val timeUp = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

                notification()

            }

        }
        timeUp.start()

    }

}