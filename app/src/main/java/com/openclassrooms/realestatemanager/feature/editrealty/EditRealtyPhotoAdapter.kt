package com.openclassrooms.realestatemanager.feature.editrealty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.data.model.Photo
import com.openclassrooms.realestatemanager.databinding.EditPhotoItemBinding

class EditRealtyPhotoAdapter(startingData: List<Photo>, private val onDeletePhoto: (photo: Photo) -> Unit, private val onRenamePhoto: (photo: Photo) -> Unit): RecyclerView.Adapter<EditRealtyPhotoAdapter.ViewHolder>() {

    private val photos: MutableList<Photo> = startingData.toMutableList()

    fun updateData(newData: List<Photo>) {
        with(photos) {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        EditPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position], onDeletePhoto, onRenamePhoto)
    }

    class ViewHolder(private val binding: EditPhotoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, onDeletePhoto: (photo: Photo) -> Unit, onRenamePhoto: (photo: Photo) -> Unit) {
            binding.photo = photo
            binding.removePhotoButton.setOnClickListener {
                onDeletePhoto(photo)
            }
            binding.editPhotoNameButton.setOnClickListener {
                onRenamePhoto(photo)
            }
        }
    }
}
