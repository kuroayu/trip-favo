package com.kuro.trip_favo.data.repositry

import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.database.FavoriteHotelDao

class FavoriteHotelRepository(private val favoriteHotelDao: FavoriteHotelDao) {

    suspend fun getAllHotelData(): List<FavoriteHotel> {
        return favoriteHotelDao.allHotelData()
    }

    suspend fun insert(favoriteHotel: FavoriteHotel) {
        favoriteHotelDao.insert(favoriteHotel)
    }

    suspend fun delete(favoriteHotel: FavoriteHotel) {
        favoriteHotelDao.delete(favoriteHotel)
    }
}