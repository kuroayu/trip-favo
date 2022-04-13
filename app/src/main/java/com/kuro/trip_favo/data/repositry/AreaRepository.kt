package com.kuro.trip_favo.data.repositry

import com.kuro.trip_favo.data.api.ApiClient
import com.kuro.trip_favo.data.api.RakutenAreaResult
import retrofit2.Response

class AreaRepository {

    suspend fun getArea(): Response<RakutenAreaResult> {
        return ApiClient.retrofit.getRakutenArea()
    }
}