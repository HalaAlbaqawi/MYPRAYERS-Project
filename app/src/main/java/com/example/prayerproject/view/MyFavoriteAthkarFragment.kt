package com.example.prayerproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.adapter.AthkarAdapter
import com.example.prayerproject.adapter.MyFavoriteAthkarAdapter
import com.example.prayerproject.databinding.FragmentAthkarBinding
import com.example.prayerproject.databinding.FragmentMyFavoriteAthkarBinding




private const val TAG = "MyFavoriteAthkarFragmen"
class MyFavoriteAthkarFragment : Fragment() {


    private lateinit var binding: FragmentMyFavoriteAthkarBinding
    private lateinit var myFavoriteAthkarAdapter: MyFavoriteAthkarAdapter
    private val myFavoriteAthkarViewModel: MyFavoriteAthkarViewModel by activityViewModels()


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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "my athkar fragment")
        observers()

        myFavoriteAthkarViewModel.callData()

        myFavoriteAthkarAdapter = MyFavoriteAthkarAdapter(myFavoriteAthkarViewModel, requireActivity())
        binding.myathkarRecyclerView.adapter = myFavoriteAthkarAdapter

    }

    fun observers(){
     myFavoriteAthkarViewModel.myAthkarLiveData.observe(viewLifecycleOwner,{
         Log.d(TAG,"$it")
     myFavoriteAthkarAdapter.submitList(it)
     })
     binding.progressbarMyfav.animate().alpha(0f)


    }
    }
