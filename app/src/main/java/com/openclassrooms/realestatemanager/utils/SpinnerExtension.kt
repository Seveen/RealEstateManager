package com.openclassrooms.realestatemanager.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.openclassrooms.realestatemanager.data.model.EstateAgent

fun Spinner.setText(text: String) {
    val adapter: ArrayAdapter<String>? = this.adapter as ArrayAdapter<String>
    val position: Int? = adapter?.getPosition(text)
    setSelection(position ?: 0)
}

fun Spinner.onSelected(listener: (String?) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val adapter: ArrayAdapter<String>? = this@onSelected.adapter as ArrayAdapter<String>
            listener(adapter?.getItem(position))
        }
        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
}

fun Spinner.onAgentSelected(listener: (EstateAgent?) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val adapter: ArrayAdapter<EstateAgent> = this@onAgentSelected.adapter as ArrayAdapter<EstateAgent>
            listener(adapter.getItem(position))
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}