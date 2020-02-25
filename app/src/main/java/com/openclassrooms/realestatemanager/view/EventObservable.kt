package com.openclassrooms.realestatemanager.view

import io.reactivex.Observable

/**
 * This allows us to group all the viewEvents from
 * one view in a single Observable.
 */
interface EventObservable<E> {
    fun events(): Observable<E>
}