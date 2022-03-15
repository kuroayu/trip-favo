package com.kuro.trip_favo.data.api

import kotlinx.serialization.Serializable

@Serializable
data class RakutenHotelResult(
    val hotels: List<HotelBasicInfo>
)

@Serializable
data class HotelBasicInfo(
    val access: String,
    val address1: String,
    val address2: String,
    val dpPlanListUrl: String,
    val faxNo: String,
    val hotelImageUrl: String,
    val hotelInformationUrl: String,
    val hotelKanaName: String,
    val hotelMapImageUrl: String,
    val hotelMinCharge: Int,
    val hotelName: String,
    val hotelNo: Int,
    val hotelSpecial: String,
    val hotelThumbnailUrl: String,
    val latitude: Double,
    val longitude: Double,
    val nearestStation: String,
    val parkingInformation: String,
    val planListUrl: String,
    val postalCode: String,
    val reviewAverage: Double,
    val reviewCount: Int,
    val reviewUrl: String,
    val roomImageUrl: String,
    val roomThumbnailUrl: String,
    val telephoneNo: String
)