package com.example.prayerproject.adapter


import android.app.ActivityManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prayerproject.R
import com.example.prayerproject.Service.AlarmService
import com.example.prayerproject.databinding.MyduaaItemLayoutBinding
import com.example.prayerproject.model.DuaaModel
import com.example.prayerproject.view.MyFavoriteDuaaViewModel


class MyFavoriteDuaaAdapter(
    val myFavoriteDuaaViewModel: MyFavoriteDuaaViewModel,
    val context: Context
) : RecyclerView.Adapter<MyFavoriteDuaaAdapter.MyFavoriteAthkarViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DuaaModel>() {

        override fun areItemsTheSame(oldItem: DuaaModel, newItem: DuaaModel): Boolean {
            // we should use id but it could be any thing unique like username
            return oldItem.title == newItem.title
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
            MyduaaItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyFavoriteAthkarViewHolder(binding, myFavoriteDuaaViewModel)
    }


    override fun onBindViewHolder(holder: MyFavoriteAthkarViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.binding.deleteImageButton.setOnClickListener {
            Toast.makeText(context, "Dua'a has been removed from your list", Toast.LENGTH_SHORT)
                .show()
            val myAthkar = mutableListOf<DuaaModel>()
            myAthkar.addAll(differ.currentList)
            myAthkar.remove(item)
            myFavoriteDuaaViewModel.deleteAthkar(item.id)
            differ.submitList(myAthkar)

//            isMyServiceRunning(AlarmService::class.java)

        }

        // to svae the alarm button state when its pressed
        holder.binding.alarmToggleButton.setOnClickListener {
            Log.d(TAG, "inside the edit")
            if (holder.binding.alarmToggleButton.isChecked) {
                timePicker(item)


            } else {
                item.alarm = false
                myFavoriteDuaaViewModel.editAthkar(item)

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
            item.alarm = true

            startStopService(item.title,item.duaa,hour,minute)

            myFavoriteDuaaViewModel.editAthkar(item)

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

    class MyFavoriteAthkarViewHolder(
        val binding: MyduaaItemLayoutBinding,
        val myFavoriteDuaaViewModel: MyFavoriteDuaaViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(duaaModel: DuaaModel) {

            binding.duaaTextview.text = duaaModel.duaa
            binding.titleTextview.text = duaaModel.title
            binding.alarmToggleButton.isChecked = duaaModel.alarm
            val deleteImageButton = binding.deleteImageButton
            val alarmToggleButton = binding.alarmToggleButton

        }

    }


    private fun startStopService(title: String, text: String, hour: Int, minute: Int) {


        Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, AlarmService::class.java)
        intent.putExtra("title",title)
        intent.putExtra("text",text)
        intent.putExtra("hour",hour)
        intent.putExtra("minute",minute)
        context.startService(intent)

    }

    private fun isMyServiceRunning(mClass: Class<AlarmService>): Boolean {

        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service: ActivityManager.RunningServiceInfo in
        manager.getRunningServices(Integer.MAX_VALUE)) {

            if (mClass.name.equals(service.service.className)) {
                return true

            }
        }
        return false
    }


}
