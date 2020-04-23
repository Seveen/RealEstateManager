package com.openclassrooms.realestatemanager.feature.editrealty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.model.EstateAgent
import com.openclassrooms.realestatemanager.data.model.Photo
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EditRealtyViewModel(
        private val realtyRepository: RealtyRepository,
        private val agentRepository: AgentRepository
) : ViewModel() {

    val currentRealty = realtyRepository.currentRealty

    val allAgents = agentRepository.getAllAgents().asLiveData()

    fun editType(type: String) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(type = type))

    fun editPrice(price: Double) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(priceInDollars = price))

    fun editSurface(surface: Double) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(surface = surface))

    fun editNbOfRooms(nbRooms: Int) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(numberOfRooms = nbRooms))

    fun editNbOfBedrooms(nbBedrooms: Int) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(numberOfBedrooms = nbBedrooms))

    fun editNbOfBathrooms(nbBathrooms: Int) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(numberOfBathrooms = nbBathrooms))

    fun editDistrict(district: String) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(district = district))

    fun editAddress(address: String) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(address = address))

    fun editDescription(description: String) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(description = description))

    fun editPoiSubway(newValue: Boolean) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(isCloseToSubway = newValue))

    fun editPoiShops(newValue: Boolean) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(isCloseToShops = newValue))

    fun editPoiPark(newValue: Boolean) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(isCloseToPark = newValue))

    fun editSold(newValue: Boolean) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(isSold = newValue))

    fun addPhoto(photo: Photo) =
            realtyRepository.currentRealty.value?.let {
                realtyRepository.setCurrentRealty(it.copy(photos = it.photos.plus(photo)))
            }

    fun editAgent(agent: EstateAgent) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(assignedEstateAgentId = agent.id))

    fun editSaleDate(date: Date?) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(saleDate = date))

    fun editMarketEntryDate(date: Date) =
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(marketEntryDate = date))

    fun removePhoto(photo: Photo) =
            realtyRepository.currentRealty.value?.let {
                realtyRepository.setCurrentRealty(it.copy(photos = it.photos.minus(photo)))
            }

    fun saveAndThen(isNetworkAvailable: Boolean, doNext: () -> Unit, doOnError: () -> Unit) = viewModelScope.launch {
        var saved = false
        withContext(Dispatchers.IO) {
            saved = realtyRepository.saveCurrentRealty(isNetworkAvailable)
        }
        if (saved) doNext() else doOnError()
    }
}