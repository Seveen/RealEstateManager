package com.openclassrooms.realestatemanager.utils

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*

@BindingConversion
fun convertDoubleToString(value: Double) = value.toString()

@BindingAdapter("textFromInt")
fun setTextFromInt(view: TextView, value: Int) {
    view.text = value.toString().toEditable()
}

@BindingAdapter("priceText")
fun setPriceText(view: TextView, price: Double) {
    view.text = "$${String.format("%,.0f", price)}"
}

@BindingAdapter("roundedText")
fun setRoundedText(view: TextView, value: Double) {
    view.text = "${String.format("%,.0f", value)}"
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