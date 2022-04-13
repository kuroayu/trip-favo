package com.kuro.trip_favo.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiClient {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
    private val contentType = "application/json".toMediaType()
    private val format = Json { ignoreUnknownKeys = true }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://app.rakuten.co.jp")
        .client(httpClient)
        .addConverterFactory(format.asConverterFactory(contentType))
        .build()
        .create(RakutenService::class.java)

}