package com.example.prayerproject.view

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.R
import com.example.prayerproject.databinding.FragmentHomeBinding
import com.example.prayerproject.model.PrayerModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private lateinit var logout: MenuItem
    private lateinit var binding: FragmentHomeBinding
 private val homeViewModel: HomeViewModel by activityViewModels ()


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
        requireActivity().menuInflater.inflate(R.menu.main_menu,menu)
        logout = menu.findItem(R.id.logout_item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val formatter = DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now())
        binding.timeTextView.text = formatter.toString()



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
        binding.gregorianDateTextView.text = monthName
        Log.d(TAG, monthName)


        val formatter2 = DateTimeFormatter.ofPattern("MMMM")
        val formatted1 = formatter2.format(hijrahDate)
        binding.hijriTextView.text = formatted1
        Log.d(TAG, formatted1)

    }



}
