package com.example.prayerproject.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.prayerproject.R
import java.lang.Exception

class AboutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: ImageButton = view.findViewById(R.id.website_button)
        button.setOnClickListener {
            //implicit intent to open website
            try {
                val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://aladhan.com/about"))
                startActivity(websiteIntent)

            }catch (e:ActivityNotFoundException){
                Toast.makeText(
                    requireActivity(),
                    "No app can satisfy this request", Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }


}