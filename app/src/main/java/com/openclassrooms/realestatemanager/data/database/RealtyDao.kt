package com.openclassrooms.realestatemanager.data.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.data.model.Realty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface RealtyDao {

    @Query("SELECT * from realty_table")
    fun getAllRealty(): Flow<List<Realty>>

    fun getAllRealtyDistinctUntilChanged() = getAllRealty().distinctUntilChanged()

    @Query("SELECT * from realty_table WHERE id = :id")
    fun getRealtyById(id: Int): Flow<Realty>

    @RawQuery(observedEntities = [Realty::class])
    fun getRealtyViaQuery(query: SupportSQLiteQuery): Flow<Realty>

    @Update
    suspend fun update(realty: Realty)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(realty: Realty)
}