package com.example.prayerproject.view

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.R
import com.example.prayerproject.databinding.FragmentMenuBinding
import com.example.prayerproject.databinding.FragmentPrayersTimeBinding
import com.example.prayerproject.model.Timings
import com.google.android.gms.location.LocationServices
import java.lang.Exception
import com.google.android.gms.location.FusedLocationProviderClient

val LOCATION_PERMISSION_REQ_CODE = 1000
private const val TAG = "PrayersTimeFragment"

class PrayersTimeFragment : Fragment() {

    private lateinit var binding: FragmentPrayersTimeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val prayersTimeViewModel: PrayersTimeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrayersTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "in fragment")

        observers()

        getCurrentLocation()

    }


    private fun getCurrentLocation() {

        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // getting prayer times based on user location
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                // getting the last known or current location
                val latitude = location.latitude
                val longitude = location.longitude
                Log.d("aaaaa", "$latitude,$longitude")
                prayersTimeViewModel.callData(latitude, longitude)


            }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(), "Failed on getting current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), LOCATION_PERMISSION_REQ_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getCurrentLocation()
    }



    fun observers() {
        prayersTimeViewModel.prayerLiveData.observe(viewLifecycleOwner, {
            binding.progressBarPrayerTime.animate().alpha(0f)
            binding.fajrTextView.text = "Fajr   ${it.data.timings.fajr}"
            binding.sunriseTextView.text = "Sunrise   ${it.data.timings.sunrise}"
            binding.dhuhrTextView.text = "Dhuhr   ${it.data.timings.dhuhr}"
            binding.asrTextView.text = "Asr   ${it.data.timings.asr}"
            binding.maghribTextView.text = "Maghrib   ${it.data.timings.maghrib}"
            binding.ishaaTextView.text = "Isha'a   ${it.data.timings.isha}"
        })

    }
}