package com.openclassrooms.realestatemanager.feature.editrealty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.model.Photo
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditRealtyViewModel(
        private val realtyRepository: RealtyRepository
) : ViewModel() {

    val currentRealty = realtyRepository.currentRealty

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

    fun editPoiMetro(newValue: Boolean) =
        realtyRepository.currentRealty.value?.pointsOfInterest?.let {
            realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(pointsOfInterest = it.copy(closeToMetro = newValue)))
        }

    fun editPoiShops(newValue: Boolean) =
            realtyRepository.currentRealty.value?.pointsOfInterest?.let {
                realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(pointsOfInterest = it.copy(closeToShops = newValue)))
            }

    fun editPoiPark(newValue: Boolean) =
            realtyRepository.currentRealty.value?.pointsOfInterest?.let {
                realtyRepository.setCurrentRealty(realtyRepository.currentRealty.value?.copy(pointsOfInterest = it.copy(closeToPark = newValue)))
            }

    fun addPhoto(photo: Photo) =
            realtyRepository.currentRealty.value?.let {
                realtyRepository.setCurrentRealty(it.copy(photos = it.photos.plus(photo)))
            }

    fun removePhoto(photo: Photo) =
            realtyRepository.currentRealty.value?.let {
                realtyRepository.setCurrentRealty(it.copy(photos = it.photos.minus(photo)))
            }

    fun saveAndThen(doNext: () -> Unit, doOnError: () -> Unit) = viewModelScope.launch {
        var saved = false
        withContext(Dispatchers.IO) {
            saved = realtyRepository.saveCurrentRealty()
        }
        if (saved) doNext() else doOnError()
    }
}