package com.openclassrooms.realestatemanager.feature.editrealty

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.toEditable
import kotlinx.android.synthetic.main.dialog_edit_photo_name.view.*

class EditPhotoNameDialogFragment(private val startingName: String = "", private val callback: (String) -> Unit): DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                    .setTitle("Photo name")
                    .setView(it.layoutInflater.inflate(R.layout.dialog_edit_photo_name, null).also { view ->
                        view.photoNameLayoutView?.editText?.text = startingName.toEditable()
                    })
                    .setNegativeButton("Cancel") { _, _ -> dialog?.cancel() }
                    .setPositiveButton("Save") { _, _ ->
                        callback(
                                dialog?.findViewById<TextInputLayout>(R.id.photoNameLayoutView)?.
                                editText?.
                                text.toString()
                        )
                        dismiss()
                    }
                    .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}