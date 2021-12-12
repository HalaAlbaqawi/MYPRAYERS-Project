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
import java.time.LocalDateTime
import java.time.LocalTime
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
        observers()
//        val current = LocalDateTime.now()
//
//        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
//        val formatted = current.format(formatter)
//        binding.timeTextView.text = formatted
        val formatter = DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now())
        binding.timeTextView.text = formatter.toString()

//        dateFormat = new SimpleDateFormat("hh:mm a")


        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        binding.gregorianDateTextView.text = currentDate
    }



    fun observers(){
     homeViewModel.homeLiveData.observe(viewLifecycleOwner,{
         binding.hijriDateTextView.text = "${it.data.date.hijri.date}"
         Log.d(TAG,"no data".toString())

     })


    }
}