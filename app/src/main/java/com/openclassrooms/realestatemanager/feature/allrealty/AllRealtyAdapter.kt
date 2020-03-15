package com.openclassrooms.realestatemanager.feature.allrealty

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.databinding.RealtyListItemBinding

class AllRealtyAdapter(startingData: List<Realty>, private val onClick: (Realty) -> Unit): RecyclerView.Adapter<AllRealtyAdapter.ViewHolder>() {

    private val realtyList: MutableList<Realty> = startingData.toMutableList()

    fun updateData(newData: List<Realty>) {
        with(realtyList) {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            RealtyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = realtyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(realtyList[position], onClick)
    }

    class ViewHolder(private val binding: RealtyListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(realty: Realty, onClick: (Realty) -> Unit) {
            binding.realty = realty
            binding.cardView.setOnClickListener {
                onClick(realty)
            }
        }
    }
}

@BindingAdapter("priceText")
fun setPriceText(view: TextView, price: Double) {
    view.text = "$${price}"
}