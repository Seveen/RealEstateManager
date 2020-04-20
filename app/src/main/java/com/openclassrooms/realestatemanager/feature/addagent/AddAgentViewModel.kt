package com.openclassrooms.realestatemanager.feature.addagent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.model.EstateAgent
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAgentViewModel(
        private val agentRepository: AgentRepository
): ViewModel() {

    private val currentAgent = MutableLiveData(EstateAgent.default())

    fun updateName(name: String) {
        currentAgent.value = currentAgent.value?.copy(name = name)
    }

    fun updateSurname(surname: String) {
        currentAgent.value = currentAgent.value?.copy(surname = surname)
    }

    fun saveAndThen(doNext: () -> Unit) = viewModelScope.launch {
        currentAgent.value?.let {
            withContext(Dispatchers.IO) {
                agentRepository.addAgent(it)
            }
            doNext()
        }
    }
}