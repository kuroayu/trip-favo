package com.kuro.trip_favo.data.api

import retrofit2.http.GET

interface RakutenService {

    @GET
    fun getRakutenService(): retrofit2.Call<RakutenHotelResult>
}