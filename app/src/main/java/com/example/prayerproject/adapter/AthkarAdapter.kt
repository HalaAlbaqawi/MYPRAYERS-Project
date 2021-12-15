package com.example.prayerproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prayerproject.databinding.AthkarItemLayoutBinding
import com.example.prayerproject.model.AthkarModel
import com.example.prayerproject.view.AthkarViewModel

const val TAG = "AthkarAdapter"
class AthkarAdapter(val athkarViewModel: AthkarViewModel):RecyclerView.Adapter<AthkarAdapter.AthkarViewHolder>() {


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AthkarModel>() {
        override fun areItemsTheSame(oldItem: AthkarModel, newItem: AthkarModel): Boolean {
            // we should use id but it could be any thing unique like username
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AthkarModel, newItem: AthkarModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<AthkarModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AthkarViewHolder {

        val binding =
            AthkarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AthkarViewHolder(binding,athkarViewModel)
    }


    override fun onBindViewHolder(holder: AthkarViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
           holder.binding.addImagebutton.setOnClickListener {
               athkarViewModel.addAthkar(item)
           }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

     class AthkarViewHolder(val binding: AthkarItemLayoutBinding, val athkarViewModel: AthkarViewModel,) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(athkarModel: AthkarModel) {

            binding.athkarTextview.text = athkarModel.athkar
            binding.titleTextview.text = athkarModel.title
         val addbutton = binding.addImagebutton
         }

        }
    }