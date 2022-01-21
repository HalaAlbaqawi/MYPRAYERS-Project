package com.example.prayerproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.prayerproject.Service.AlarmService
import com.example.prayerproject.adapter.MyFavoriteDuaaAdapter
import com.example.prayerproject.databinding.FragmentMyFavoriteDuaaBinding


private const val TAG = "MyFavoriteDuaaFragment"

class MyFavoriteDuaaFragment() : Fragment() {


    private lateinit var binding: FragmentMyFavoriteDuaaBinding
    private lateinit var myFavoriteDuaaAdapter: MyFavoriteDuaaAdapter
    private val myFavoriteDuaaViewModel: MyFavoriteDuaaViewModel by activityViewModels()
    val alarmService = AlarmService()


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

        Log.d(TAG, "my Dua'a fragment")
        observers()


        myFavoriteDuaaAdapter =
            MyFavoriteDuaaAdapter(myFavoriteDuaaViewModel, requireActivity())
        binding.myduaaRecyclerView.adapter = myFavoriteDuaaAdapter

        myFavoriteDuaaViewModel.callData()

    }

    fun observers() {
        myFavoriteDuaaViewModel.myDuaaLiveData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "$it")
            binding.progressbarMyfav.animate().alpha(0f)
            myFavoriteDuaaAdapter.submitList(it)


            //checking the list if it's empty then show empty list message to the user
            if (it.isEmpty()) {
                binding.messageTextView.visibility = View.VISIBLE
            } else {
                binding.messageTextView.visibility = View.GONE

            }

        })


    }


}
