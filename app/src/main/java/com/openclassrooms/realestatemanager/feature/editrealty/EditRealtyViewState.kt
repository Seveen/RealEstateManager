package com.openclassrooms.realestatemanager.feature.editrealty

import com.openclassrooms.realestatemanager.data.model.EstateAgent
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviViewState
import java.util.*

data class EditRealtyViewState(
        val isProcessing: Boolean,
        val realty: Realty,
        val isSaveComplete: Boolean,
        val error: Throwable?
) : MviViewState {
    companion object {
        fun default() = EditRealtyViewState(
                isProcessing = false,
                realty = Realty("default", "type", 0.0, 0.0,
                        0, 0, 0, "", "", emptyList(),
                        false, Date(), Date(), EstateAgent(0, "", ""),
                        "", emptyList()),
                isSaveComplete = false,
                error = null
        )
    }
}