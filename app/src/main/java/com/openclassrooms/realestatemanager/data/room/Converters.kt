package com.openclassrooms.realestatemanager.data.room

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.data.model.Photo
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun serializeLatLng(location: LatLng?): String? = location?.let {
        Gson().toJson(location)
    } ?: ""

    @TypeConverter
    fun deserializeLatLng(location: String): LatLng? {
        return fromJson<LatLng>(location)
    }

    @TypeConverter
    fun serializePhotos(photos: List<Photo>): String? = Gson().toJson(photos)

    @TypeConverter
    fun deserializePhotos(photos: String): List<Photo>? {
        return fromJson<List<Photo>>(photos)
    }

    private inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object: TypeToken<T>(){}.type)
    }

}
