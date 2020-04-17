package com.openclassrooms.realestatemanager.data.database

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.data.model.Realty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface RealtyDao {

    @Query("SELECT * from realty_table")
    fun getAllRealty(): Flow<List<Realty>>

    @Query("SELECT * from realty_table WHERE id = :id")
    fun getRealtyCursorById(id: Int): Cursor

    fun getAllRealtyDistinctUntilChanged() = getAllRealty().distinctUntilChanged()

    @Query("SELECT * from realty_table WHERE id = :id")
    fun getRealtyById(id: Int): Flow<Realty>

    @RawQuery(observedEntities = [Realty::class])
    fun getRealtyViaQuery(query: SupportSQLiteQuery): Flow<List<Realty>>

    @Update
    suspend fun update(realty: Realty): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(realty: Realty): Long

    @Query("DELETE from realty_table WHERE id = :realtyId")
    suspend fun delete(realtyId: Int): Int
}