package com.openclassrooms.realestatemanager.data.repository

//class RoomRepository(private val realtyDao: RealtyDao,
//                     private val schedulers: BaseSchedulerProvider) : RealtyRepository {
//
//    override fun saveRealty(realty: Realty): Observable<Boolean> {
//        realtyDao.insert(realty)
//        return Observable.just(true)
//                .subscribeOn(schedulers.io())
//                .observeOn(schedulers.ui())
//    }
//
//    override fun getAllRealty(): Observable<List<Realty>> {
//        return realtyDao.getAllRealty()
//                .subscribeOn(schedulers.io())
//                .observeOn(schedulers.ui())
//    }
//
//    override fun clearAllRealty(): Observable<Boolean> {
//        //TODO implement
//        return Observable.just(true)
//                .subscribeOn(schedulers.io())
//                .observeOn(schedulers.ui())
//    }
//
//    override fun getRealtyById(id: String): Observable<Realty> {
//        return realtyDao.getRealtyById(id)
//                .subscribeOn(schedulers.io())
//                .observeOn(schedulers.ui())
//    }
//
//}