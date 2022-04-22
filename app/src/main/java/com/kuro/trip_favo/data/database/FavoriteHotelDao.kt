package com.kuro.trip_favo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteHotelDao {
    @Query("SELECT * FROM hotel_table ORDER BY hotelNumber DESC")
    fun AllHotelData(): LiveData<List<FavoriteHotel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hotelData: FavoriteHotel)
}