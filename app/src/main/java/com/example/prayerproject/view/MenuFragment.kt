package com.example.prayerproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.prayerproject.R
import com.example.prayerproject.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     // when the user clicks on the card view it will navigate from menu fragment to duaa fragment
     // and from menu fragment to my favorite duaa fragment

        binding.duaaCardView.setOnClickListener {
         // to navigate between fragments
            findNavController().navigate(R.id.action_menuFragment_to_athkarFragment)
        }

        binding.duaaListCardView.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_myFavoriteDuaaFragment)

        }

    }
}