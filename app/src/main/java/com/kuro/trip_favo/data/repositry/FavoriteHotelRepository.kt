package com.kuro.trip_favo.data.repositry

import androidx.lifecycle.LiveData
import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.database.FavoriteHotelDao

//1viewModelに対して1repositoryなのか、1アクセス先に対して1repositoryなのか
class FavoriteHotelRepository(private val favoriteHotelDao: FavoriteHotelDao) {
    val allHotelData: LiveData<List<FavoriteHotel>> = favoriteHotelDao.AllHotelData()

    suspend fun insert(favoriteHotel: FavoriteHotel) {
        favoriteHotelDao.insert(favoriteHotel)
    }
}