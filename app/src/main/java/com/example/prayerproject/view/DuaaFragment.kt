package com.example.prayerproject.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.prayerproject.adapter.AthkarAdapter
import com.example.prayerproject.databinding.FragmentAthkarBinding

private const val TAG = "AthkarFragment"
class AthkarFragment : Fragment() {

    private lateinit var binding: FragmentAthkarBinding
    private lateinit var athkarAdapter: AthkarAdapter
    private val athkarViewModel: AthkarViewModel by activityViewModels ()
    private val myFavoriteAthkarViewModel: MyFavoriteAthkarViewModel by activityViewModels()
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




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "athkar fragment")
        observers()

        athkarViewModel.callData()

        athkarAdapter = AthkarAdapter(athkarViewModel, myFavoriteAthkarViewModel)
        binding.athkarRecyclerView.adapter = athkarAdapter


    }


    fun observers(){
    athkarViewModel.athkarLiveData.observe(viewLifecycleOwner,{
        athkarAdapter.submitList(it)
        binding.progressBarAthkar.animate().alpha(0f)

    })


    }
}