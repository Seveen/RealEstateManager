package com.openclassrooms.realestatemanager.feature.editrealty


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.openclassrooms.realestatemanager.R

/**
 * A simple [Fragment] subclass.
 */
class EditRealtyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_realty, container, false)
    }


}