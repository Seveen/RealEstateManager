package com.openclassrooms.realestatemanager.data.model

import androidx.room.ColumnInfo

data class EstateAgent(
        @ColumnInfo(name = "agent_id")val id: Int,
        val name: String,
        val surname: String
) {
    companion object {
        fun default() = EstateAgent(0, "", "")
    }
}