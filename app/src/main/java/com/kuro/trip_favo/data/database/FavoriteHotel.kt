package com.kuro.trip_favo.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotel_table")
data class FavoriteHotel(
    @PrimaryKey
    val hotelNumber: Int,
    val date: Long,
    val onsen: Int,
    val hotelName: String,
    val imageUrl: String,
    val informationUrl: String,
    val address1: String,
    val address2: String,
    val reviewAverage: Double,
    val hotelMinCharge: Int
)

