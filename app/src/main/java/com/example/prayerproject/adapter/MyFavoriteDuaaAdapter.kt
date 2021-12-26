package com.example.prayerproject.adapter


import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prayerproject.databinding.MyathkarItemLayoutBinding
import com.example.prayerproject.model.DuaaModel
import com.example.prayerproject.view.MyFavoriteAthkarViewModel


class MyFavoriteDuaaAdapter(
    val myFavoriteAthkarViewModel: MyFavoriteAthkarViewModel,
    val context: Context
) : RecyclerView.Adapter<MyFavoriteDuaaAdapter.MyFavoriteAthkarViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DuaaModel>() {

        override fun areItemsTheSame(oldItem: DuaaModel, newItem: DuaaModel): Boolean {
            // we should use id but it could be any thing unique like username
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DuaaModel, newItem: DuaaModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<DuaaModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyFavoriteAthkarViewHolder {

        val binding =
            MyathkarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyFavoriteAthkarViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyFavoriteAthkarViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.binding.deleteImageButton.setOnClickListener {

            val myAthkar = mutableListOf<DuaaModel>()
            myAthkar.addAll(differ.currentList)
            myAthkar.remove(item)
            myFavoriteAthkarViewModel.deleteAthkar(item.id)
            differ.submitList(myAthkar)

        }


        holder.binding.toggleButton.setOnClickListener {
            Log.d(TAG, "inside the edit")
            if (holder.binding.toggleButton.isChecked) {

                timePicker(item)
            } else {
                item.isNotify = false
                myFavoriteAthkarViewModel.editAthkar(item)
            }
        }
    }

    // time picker dialog on favorite duaa to remind the user what time he wants
    fun timePicker(item: DuaaModel) {

        var timeSet: String = ""
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            timeSet = "$hour:$minute"

            Log.d("TimePicker", timeSet)
            item.isNotify = true
            item.time = timeSet

            myFavoriteAthkarViewModel.editAthkar(item)

        }
        val time = TimePickerDialog(
            context,
            timeSetListener,
            0,
            0,
            true
        )

        time.show()

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class MyFavoriteAthkarViewHolder(val binding: MyathkarItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(athkarModel: DuaaModel) {

            binding.athkarTextview.text = athkarModel.duaa
            binding.titleTextview.text = athkarModel.title
            val deleteImageButton = binding.deleteImageButton
            val notificationToggleButton = binding.toggleButton


        }


    }
}