package com.kuro.trip_favo.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotel_table")
data class FavoriteHotel(
    @PrimaryKey
    val hotelNumber: Int,
    val date: Long,
    //温泉で絞り込んだものに数字を割り振る予定(0=温泉なし、1=温泉あり)
    val onsen: Int,
    val hotelName: String,
    val imageUrl: String,
    val informationUrl: String,
    val address1: String,
    val address2: String,
    val reviewAverage: Double,
    val hotelMinCharge: Int
)

