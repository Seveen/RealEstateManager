package com.openclassrooms.realestatemanager.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.data.model.Realty
import io.reactivex.Observable

@Dao
interface RealtyDao {

    @Query("SELECT * from realty_table")
    fun getAllRealty(): Observable<List<Realty>>

    @Query("SELECT * from realty_table WHERE id = :id")
    fun getRealtyById(id: String): Observable<Realty>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(realty: Realty)
}