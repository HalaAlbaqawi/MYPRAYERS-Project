package com.example.prayerproject.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.R
import com.example.prayerproject.api.QiblaApi
import com.example.prayerproject.databinding.FragmentPrayersTimeBinding
import com.example.prayerproject.databinding.FragmentQiblaBinding
import com.example.prayerproject.model.QiblaModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "QiblaFragment"

class QiblaFragment : Fragment(), SensorEventListener {

    private var currentDegree = 0f
    private var qibla: Double? = null
    private var mSensorManager: SensorManager? = null

    private val qiblaViewModel: QiblaViewModel by activityViewModels()

    private lateinit var binding: FragmentQiblaBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQiblaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "view created")
        getQiblatDirection()
        observers()
        initData()
    }


    private fun initData() {
        mSensorManager =
            requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    }

    override fun onResume() {
        super.onResume()
        @Suppress("DEP")
        mSensorManager?.registerListener(
            this, mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }


    override fun onSensorChanged(event: SensorEvent?) {
        val degree = (event?.values?.get(0)!!)
        val rotateAnimation = RotateAnimation(
            currentDegree,
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 210
        rotateAnimation.fillAfter = true

        binding.constraint.startAnimation(rotateAnimation)

        currentDegree = (-degree).toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }


    fun getQiblatDirection() {
        Log.d(TAG, "Tes2323t")

        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Test")

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                // getting the last known or current location

                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    Log.d(TAG, "$latitude,$longitude")
                    qiblaViewModel.getQibla(latitude, longitude)

                }


            }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(), "Failed on getting current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }


        } else {
            Log.d(TAG, "Test1")

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
        //getQiblatDirection()
    }

    fun observers() {
        qiblaViewModel.qiblaLiveData.observe(viewLifecycleOwner, {

            val rotateAnimation = RotateAnimation(
                currentDegree,
                it.toFloat(),
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotateAnimation.duration = 210
            rotateAnimation.fillAfter = true

            binding.constraintLayout.startAnimation(rotateAnimation)
        })

    }
}