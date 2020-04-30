package com.openclassrooms.realestatemanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.data.database.AgentDao
import com.openclassrooms.realestatemanager.data.database.RealtyDao
import com.openclassrooms.realestatemanager.data.database.RealtyDatabase
import com.openclassrooms.realestatemanager.data.model.EstateAgent
import com.openclassrooms.realestatemanager.data.model.Realty
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//Here we're using Flow.take(1) to run asserts on the first result of the flow, otherwise the test would never stop and hang
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var agentDao: AgentDao
    private lateinit var realtyDao: RealtyDao

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        RealtyDatabase.createInMemoryDatabase(context)
        val database = RealtyDatabase.getDatabase(context)

        agentDao = database.agentDao()
        realtyDao = database.realtyDao()
    }

    @Test
    fun getAgentsWhenNoAgentInserted() {
        val agents = runBlocking {
            agentDao.getAllAgents().take(1).toList().flatten()
        }

        assertEquals(0, agents.count())
    }

    @Test
    fun insertAndGetAgent() {
        runBlocking {
            val expectedAgent = EstateAgent.default().copy(id = 1)
            agentDao.insert(expectedAgent)
            agentDao.getAllAgents().take(1).collect {
                assertEquals(1, it.count())
            }

            agentDao.getAgentById(1).take(1).collect {
                assertEquals(expectedAgent, it)
            }
        }
    }

    @Test
    fun getRealtyWhenNoRealtyInserted() {
        val realty = runBlocking {
            realtyDao.getAllRealty().take(1).toList().flatten()
        }

        assertEquals(0, realty.count())
    }

    @Test
    fun insertAndGetRealty() {
        runBlocking {
            val expectedRealty = Realty.default().copy(id = 1)
            realtyDao.insert(expectedRealty)
            realtyDao.getAllRealty().take(1).collect {
                assertEquals(1, it.count())
            }

            realtyDao.getRealtyById(1).take(1).collect {
                assertEquals(1, it.id)
            }
        }
    }

    @Test
    fun insertAndDeleteRealty() {
        runBlocking {
            val expectedRealty = Realty.default().copy(id = 1)
            realtyDao.insert(expectedRealty)
            realtyDao.getAllRealty().take(1).collect {
                assertEquals(1, it.count())
            }

            realtyDao.delete(1)
            realtyDao.getAllRealty().take(1).collect {
                assertEquals(0, it.count())
            }
        }
    }
}