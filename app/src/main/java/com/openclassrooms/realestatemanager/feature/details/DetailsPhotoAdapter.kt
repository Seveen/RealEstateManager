package com.openclassrooms.realestatemanager.feature.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.data.model.Photo
import com.openclassrooms.realestatemanager.databinding.DetailsPhotoItemBinding

class DetailsPhotoAdapter(startingData: List<Photo>): RecyclerView.Adapter<DetailsPhotoAdapter.ViewHolder>() {

    private val photos: MutableList<Photo> = startingData.toMutableList()

    fun updateData(newData: List<Photo>) {
        with(photos) {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            DetailsPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    class ViewHolder(private val binding: DetailsPhotoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
        }
    }
}
