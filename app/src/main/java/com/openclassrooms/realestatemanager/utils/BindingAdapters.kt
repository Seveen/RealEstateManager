package com.openclassrooms.realestatemanager.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion

@BindingConversion
fun convertDoubleToString(value: Double) = value.toString()

@BindingAdapter("textFromInt")
fun setTextFromInt(view: TextView, value: Int) {
    view.text = value.toString().toEditable()
}