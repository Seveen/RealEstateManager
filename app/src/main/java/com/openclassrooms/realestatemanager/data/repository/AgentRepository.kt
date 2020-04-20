package com.openclassrooms.realestatemanager.data.repository

import com.openclassrooms.realestatemanager.data.database.AgentDao
import com.openclassrooms.realestatemanager.data.model.EstateAgent

class AgentRepository(private val agentDao: AgentDao) {
    suspend fun addAgent(agent: EstateAgent) {
        agentDao.insert(agent)
    }
}