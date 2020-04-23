package com.openclassrooms.realestatemanager.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository

class DetailsViewModel(
        private val realtyRepository: RealtyRepository,
        private val agentRepository: AgentRepository
) : ViewModel() {

    val currentRealty = realtyRepository.currentRealty

    val currentRealtyAgent = agentRepository
            .getAgentById(currentRealty.value?.assignedEstateAgentId ?: 0)
            .asLiveData()

    fun getAgentById(id: Int) = agentRepository.getAgentById(id)
            .asLiveData(viewModelScope.coroutineContext)

}