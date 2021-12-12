package com.example.prayerproject.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prayerproject.R
import com.example.prayerproject.model.PrayersModel

class AthkarAdapter(private val list: List<PrayersModel>) :
    RecyclerView.Adapter<AthkarAdapter.AthkarViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AthkarAdapter.AthkarViewHolder {
        return AthkarViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.athkar_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AthkarViewHolder, position: Int) {
        val item = list[position]
        TODO("bind view with data")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class AthkarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}