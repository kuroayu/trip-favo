package com.kuro.trip_favo.data.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RakutenService {
    @GET("/services/api/Travel/SimpleHotelSearch/20170426?format=json&largeClassCode=japan&elements=hotelBasicInfo&applicationId=1074940900517142254")
    suspend fun getRakutenHotel(
        @Query("middleClassCode") middleClassCode: String,
        @Query("smallClassCode") smallClassCode: String,
        @Query("detailClassCode") detailClassCode: String,
        @Query("squeezeCondition") squeezeCondition: String?
    ): Response<RakutenHotelResult>


    @GET("/services/api/Travel/GetAreaClass/20131024?format=json&applicationId=1074940900517142254")
    suspend fun getRakutenArea(): Response<RakutenAreaResult>
}