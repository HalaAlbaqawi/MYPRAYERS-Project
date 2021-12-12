package com.example.prayerproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prayerproject.databinding.FragmentMyFavoriteAthkarBinding



private lateinit var binding: FragmentMyFavoriteAthkarBinding

class MyFavoriteAthkarFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyFavoriteAthkarBinding.inflate(inflater, container, false)
        return binding.root
    }
    }