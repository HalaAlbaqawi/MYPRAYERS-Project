package com.example.prayerproject.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.prayerproject.R
import com.example.prayerproject.databinding.AthkarItemLayoutBinding
import com.example.prayerproject.databinding.FragmentAthkarBinding
import com.example.prayerproject.model.AthkarModel
import com.example.prayerproject.model.PrayerModel
import com.example.prayerproject.model.PrayersModel
import kotlinx.coroutines.NonDisposableHandle.parent

private const val TAG = "AthkarAdapter"
class AthkarAdapter:RecyclerView.Adapter<AthkarAdapter.AthkarViewHolder>(){


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AthkarModel>(){
        override fun areItemsTheSame(oldItem: AthkarModel, newItem: AthkarModel): Boolean {
            // we should use id but it could be any thing unique like username
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AthkarModel, newItem: AthkarModel): Boolean {
            return oldItem == newItem        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<AthkarModel>){
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AthkarViewHolder {

    val binding = AthkarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
     return AthkarViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AthkarViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class AthkarViewHolder(val binding: AthkarItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(athkarModel: AthkarModel){

            binding.athkarTextview.text = athkarModel.athkar
            binding.titleTextview.text = athkarModel.title
            Log.d(TAG, "athkar")
            
        }


    }
}