package com.kuro.trip_favo.data.repositry

import androidx.lifecycle.LiveData
import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.database.FavoriteHotelDao

class FavoriteHotelRepository(private val favoriteHotelDao: FavoriteHotelDao) {
    val AllHotelData: LiveData<List<FavoriteHotel>> = favoriteHotelDao.AllHotelData()

    suspend fun insert(favoriteHotel: FavoriteHotel) {
        favoriteHotelDao.insert(favoriteHotel)
    }
}