package com.example.prayerproject.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.R
import com.example.prayerproject.databinding.FragmentMenuBinding
import com.example.prayerproject.databinding.FragmentPrayersTimeBinding
import com.google.android.gms.location.LocationServices
import java.lang.Exception


private const val TAG = "PrayersTimeFragment"
class PrayersTimeFragment : Fragment() {

    private lateinit var binding: FragmentPrayersTimeBinding

    private val prayersTimeViewModel: PrayersTimeViewModel by activityViewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        observers()


        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                try {
                    var longitude = it.longitude
                    var latitude = it.latitude
                    prayersTimeViewModel.callData(latitude, longitude)

                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                }

            }
        }

            /*getLocation.addOnSuccessListener {

                try {
                    var longitude = it.longitude
                    var latitude = it.latitude
                    PrayersTimeViewModel.callData(latitude,longitude)

                }catch (e:Exception){
                    Log.d(TAG,e.message.toString())
                }

            }
        return*/

        }




    }


    fun observers(){
    }




