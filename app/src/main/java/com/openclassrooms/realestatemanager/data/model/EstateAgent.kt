package com.openclassrooms.realestatemanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agent_table")
data class EstateAgent(
        @PrimaryKey(autoGenerate = true)val id: Int,
        val name: String,
        val surname: String
) {
    fun displayName() = "$name $surname"

    override fun toString() = displayName()

    companion object {
        fun default() = EstateAgent(0, "", "")
    }
}