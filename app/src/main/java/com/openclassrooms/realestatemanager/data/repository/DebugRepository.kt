package com.openclassrooms.realestatemanager.data.repository

////TODO: implement
//class DebugRepository : RealtyRepository {
//
//    val dummyList: List<Realty> = listOf(
//            Realty(0, "House", 100.0, 59.0, 5, 5, 5, "district", "address", PointsOfInterest(1, closeToMetro = false, closeToShops = false), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description1", emptyList()),
//            Realty(1, "Penthouse", 140.0, 59.0, 5, 5, 5, "district", "address", PointsOfInterest(2, closeToMetro = false, closeToShops = false), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description2", emptyList()),
//            Realty(2, "type", 140.0, 59.0, 5, 5, 5, "district", "address", PointsOfInterest(3, closeToMetro = false, closeToShops = false), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description3", emptyList())
//    )
//
//    private var _currentRealty: MutableLiveData<Realty> = MutableLiveData()
//
//    override val currentRealty
//        get() = _currentRealty
//
//    override fun setCurrentRealty(realty: Realty?) {
//        Log.d("REPOSITORYLOG", "realty changed: ${realty?.description} nb: ${realty?.numberOfRooms}" )
//        _currentRealty.value = realty
//    }
//
//    override suspend fun saveRealty(realty: Realty): Boolean = true
//
//    override fun getAllRealty(): Flow<List<Realty>> = flow {
//        emit(dummyList)
//    }
//
//    override fun getRealtyById(id: Int): Flow<Realty> = flow {
//        val realty = dummyList.find { it.id == id }
//        if (realty != null) {
//            emit(realty)
//        }
//    }
//
//}