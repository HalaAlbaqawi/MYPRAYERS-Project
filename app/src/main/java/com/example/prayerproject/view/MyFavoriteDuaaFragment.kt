package com.example.prayerproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.adapter.MyFavoriteDuaaAdapter
import com.example.prayerproject.databinding.FragmentMyFavoriteDuaaBinding


private const val TAG = "MyFavoriteAthkarFragmen"

class MyFavoriteAthkarFragment() : Fragment() {


    private lateinit var binding: FragmentMyFavoriteDuaaBinding
    private lateinit var myFavoriteAthkarAdapter: MyFavoriteDuaaAdapter
    private val myFavoriteAthkarViewModel: MyFavoriteDuaaViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyFavoriteDuaaBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "my athkar fragment")
        observers()
        myFavoriteAthkarViewModel.callData()

        myFavoriteAthkarAdapter =
            MyFavoriteDuaaAdapter(myFavoriteAthkarViewModel, requireActivity())
        binding.myathkarRecyclerView.adapter = myFavoriteAthkarAdapter

    }

    fun observers() {
        myFavoriteAthkarViewModel.myAthkarLiveData.observe(viewLifecycleOwner, {
            Log.d(TAG, "$it")
            myFavoriteAthkarAdapter.submitList(it)
        })
        binding.progressbarMyfav.animate().alpha(0f).duration = 2000

    }


}
