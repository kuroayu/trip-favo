package com.kuro.trip_favo.data.database

import androidx.room.*

@Dao
interface FavoriteHotelDao {
    @Query("SELECT * FROM hotel_table ORDER BY hotelNumber DESC")
    suspend fun allHotelData(): List<FavoriteHotel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hotelData: FavoriteHotel)

    @Query("SELECT * FROM hotel_table WHERE (hotelName + address1 + address2) LIKE (:word)")
    fun searchData(word: String): List<FavoriteHotel>

    @Delete
    suspend fun delete(hotelData: FavoriteHotel)
}