package com.kuro.trip_favo.data.api

import com.kuro.trip_favo.data.database.FavoriteHotel
import kotlinx.serialization.Serializable

@Serializable
data class RakutenHotelResult(
    val hotels: List<Hotel>
)

@Serializable
data class Hotel(
    val hotel: List<HotelX>
)

@Serializable
data class HotelX(
    val hotelBasicInfo: HotelBasicInfo
)

@Serializable
data class HotelBasicInfo(
    val address1: String,
    val address2: String,
    val hotelImageUrl: String,
    val hotelInformationUrl: String,
    val hotelMinCharge: Int,
    val hotelName: String,
    val hotelNo: Int,
    val reviewAverage: Double,
) {
    fun toFavoriteHotel(date: Long, onsen: Int): FavoriteHotel {
        return FavoriteHotel(
            hotelNo,
            date,
            onsen,
            hotelName,
            hotelImageUrl,
            hotelInformationUrl,
            address1,
            address2,
            reviewAverage,
            hotelMinCharge
        )
    }
}