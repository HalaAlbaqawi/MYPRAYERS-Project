package com.example.prayerproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prayerproject.R
import com.example.prayerproject.databinding.FragmentAthkarBinding
import com.example.prayerproject.databinding.FragmentHomeBinding

class AthkarFragment : Fragment() {

    private lateinit var binding: FragmentAthkarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAthkarBinding.inflate(inflater, container, false)
        return binding.root
    }

}