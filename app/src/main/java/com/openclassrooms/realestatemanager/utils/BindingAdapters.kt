package com.openclassrooms.realestatemanager.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("textFromInt")
fun setTextFromInt(view: TextView, value: Int) {
    view.text = value.toString().toEditable()
}

@SuppressLint("SetTextI18n")
@BindingAdapter("priceText")
fun setPriceText(view: TextView, price: Double) {
    view.text = "$${String.format("%,.0f", price)}"
}

@BindingAdapter("roundedText")
fun setRoundedText(view: TextView, value: Double) {
    view.text = String.format("%,.0f", value)
}

@BindingAdapter("srcUri")
fun setImageUri(view: ImageView, uri: String) {
    view.setImageURI(Uri.parse(uri))
}

@BindingAdapter("dateText")
fun setDateText(view: TextView, date: Date?) {
    view.text = date?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(date)
    } ?: ""
}