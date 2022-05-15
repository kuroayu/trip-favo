package com.kuro.trip_favo.data.repositry


import com.kuro.trip_favo.data.api.ApiClient
import com.kuro.trip_favo.data.api.RakutenHotelResult
import retrofit2.Response

class HotelRepository {


    suspend fun getHotel(
        middleClassCode: String,
        smallClassCode: String,
        detailClassCode: String,
        squeezeCondition: String
    ): Response<RakutenHotelResult> {
        return ApiClient.retrofit.getRakutenHotel(
            middleClassCode,
            smallClassCode,
            detailClassCode,
            squeezeCondition
        )
    }
}