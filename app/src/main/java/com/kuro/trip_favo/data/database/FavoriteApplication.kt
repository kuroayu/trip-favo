package com.kuro.trip_favo.data.database

import android.app.Application
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository
import com.kuro.trip_favo.data.repositry.HotelRepository

class FavoriteApplication : Application() {

    private val database by lazy { FavoriteHotelDatabase.getDatabase(this) }

    val favoriteHotelRepository by lazy { FavoriteHotelRepository(database.favoriteHotelDao()) }

    val hotelRepository by lazy { HotelRepository() }
}