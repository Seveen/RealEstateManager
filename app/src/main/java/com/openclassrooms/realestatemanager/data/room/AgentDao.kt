package com.openclassrooms.realestatemanager.data.room

import androidx.room.*
import com.openclassrooms.realestatemanager.data.model.EstateAgent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface AgentDao {

    @Query("SELECT * from agent_table")
    fun getAllAgents(): Flow<List<EstateAgent>>

    fun getAllAgentsDistinctUntilChanged() = getAllAgents().distinctUntilChanged()

    @Query("SELECT * from agent_table WHERE id = :id")
    fun getAgentById(id: Int): Flow<EstateAgent>

    @Update
    suspend fun update(realty: EstateAgent)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(realty: EstateAgent)

}