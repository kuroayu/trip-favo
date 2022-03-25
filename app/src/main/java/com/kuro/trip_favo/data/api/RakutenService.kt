package com.kuro.trip_favo.data.api

import RakutenHotelResult
import retrofit2.http.GET
import retrofit2.http.Path

interface RakutenService {
    @GET("/services/api/Travel/SimpleHotelSearch/20170426?format=json&largeClassCode=japan&middleClassCode={middleCode}&smallClassCode={smallCode}&elements=hotelBasicInfo&applicationId=1074940900517142254")
    fun getRakutenHotel(
        @Path(
            "prefectures /{prefectures}",
            "Municipality/{middleCode}"
        )
    ): retrofit2.Call<RakutenHotelResult>

    @GET("/services/api/Travel/GetAreaClass/20131024?format=json&applicationId=1074940900517142254")
    fun getRakutenArea(): retrofit2.Call<RakutenAreaResult>
}