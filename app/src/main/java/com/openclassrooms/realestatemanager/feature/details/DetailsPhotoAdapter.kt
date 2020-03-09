package com.openclassrooms.realestatemanager.feature.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.DetailsPhotoItemBinding

class DetailsPhotoAdapter(startingData: List<String>): RecyclerView.Adapter<DetailsPhotoAdapter.ViewHolder>() {

    private val photos: MutableList<String> = startingData.toMutableList()

    fun updateData(newData: List<String>) {
        photos.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(private val binding: DetailsPhotoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: String) {

        }
    }
}
