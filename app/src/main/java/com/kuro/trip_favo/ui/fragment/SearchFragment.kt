package com.kuro.trip_favo.ui.fragment

import RakutenHotelResult
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kuro.trip_favo.R
import com.kuro.trip_favo.data.api.RakutenAreaResult
import com.kuro.trip_favo.data.api.RakutenService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        getArea(view)
        getHotel(view)

        return view
    }


    fun createService(): RakutenService {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app.rakuten.co.jp")
            .client(httpClient)
            .addConverterFactory(format.asConverterFactory(contentType))
            .build()
        return retrofit.create(RakutenService::class.java)
    }

    //地域コードの取得
    fun getArea(view: View) {
        createService().getRakutenArea().enqueue(object : retrofit2.Callback<RakutenAreaResult> {
            override fun onFailure(call: Call<RakutenAreaResult>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<RakutenAreaResult>,
                response: Response<RakutenAreaResult>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.areaClasses.largeAreas.find { it.largeClass[0].largeClassCode == "japan" }
                            ?.let {
                                val middleAreaLists = it.largeClass.drop(1).flatMap {
                                    it.middleAreas!!.map { it.middleClass[0].middleClassName }
                                }
                                val middleAreaSpinner =
                                    view.findViewById<Spinner>(R.id.Middle_spinner)
                                val middleAreaAdapter = ArrayAdapter(
                                    requireContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    middleAreaLists
                                )
                                middleAreaSpinner.adapter = middleAreaAdapter
                                middleAreaSpinner.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>,
                                            v: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            val selectedMiddlePosition =
                                                parent.getItemAtPosition(position)
                                            val selectedMiddleClassName =
                                                selectedMiddlePosition.toString()

                                            val smallAreaLists = it.largeClass.drop(1).flatMap {
                                                it.middleAreas?.find { it.middleClass[0].middleClassName == selectedMiddleClassName }
                                                    ?.let {
                                                        it.middleClass.drop(1).flatMap {
                                                            it.smallAreas!!.map { it.smallClass[0].smallClassName }
                                                        }
                                                    } ?: emptyList()
                                            }
                                            val smallAreaSpinner =
                                                view.findViewById<Spinner>(R.id.small_spinner)
                                            val smallAreaAdapter = ArrayAdapter(
                                                requireContext(),
                                                android.R.layout.simple_spinner_dropdown_item,
                                                smallAreaLists
                                            )
                                            smallAreaAdapter.notifyDataSetChanged()
                                            smallAreaSpinner?.adapter = smallAreaAdapter
                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>?) {
                                        }
                                    }
                            }
                    }
                }
            }
        })
    }

    //ホテル情報のレビュー取得
    fun getHotel(view: View) {
        createService().getRakutenHotel().enqueue(object : retrofit2.Callback<RakutenHotelResult> {
            override fun onFailure(call: Call<RakutenHotelResult>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<RakutenHotelResult>,
                response: Response<RakutenHotelResult>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val hotelInformation =
                            it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo } }

                        val ratingBar = view.findViewById<RatingBar>(R.id.ratingbar)
                        val searchBtn = view.findViewById<Button>(R.id.search_button)
                        searchBtn.setOnClickListener {
                            val ratingNow = ratingBar.rating.toDouble()
                            //選択された評価と同じホテル情報取得できてるか怪しい //リサイクラービューのonBindでやるべき?
                            hotelInformation.filter { it.reviewAverage.equals(ratingNow) }

                        }
                    }
                }
            }
        }
        )
    }
}


//                        when (ratingSelected()) {
//                            0.0 -> reviewAverage == "0.0"
//                            1.0 -> it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo.reviewAverage == 1.0 } }
//                            2.0 -> it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo.reviewAverage == 2.0 } }
//                            3.0 -> it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo.reviewAverage == 3.0 } }
//                            4.0 -> it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo.reviewAverage == 4.0 } }
//                            5.0 -> it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo.reviewAverage == 5.0 } }
//                            else -> it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo.reviewAverage == 5.0 } }
//                        }

//                        val zeroReviewHotelLists = hotelInfo.filter { it.reviewAverage < 0.0 }
//                        val oneReviewHotelLists = hotelInfo.filter { it.reviewAverage < 1.0 }
//                        val twoReviewHotelLists = hotelInfo.filter { it.reviewAverage < 2.0 }
//                        val threeReviewHotelLists = hotelInfo.filter { it.reviewAverage < 3.0 }
//                        val fourReviewHotelLists = hotelInfo.filter { it.reviewAverage < 4.0 }
//                        val fiveReviewHotelLists = hotelInfo.filter { it.reviewAverage < 5.0 }
//                        val intent = Intent(requireContext(), SearchResultFragment::class.java)

//                            if (ratingNow == 0.5) {
//                                intent.putExtra("fiveReviewHotels", fiveReviewHotelLists.toString())
//                            } else if (ratingNow == 0.4) {
//                                intent.putExtra("fourReviewHotels", fourReviewHotelLists.toString())
//                            } else if (ratingNow == 0.3) {
//                                intent.putExtra(
//                                    "threeReviewHotels", threeReviewHotelLists.toString()
//                                )
//                            } else if (ratingNow == 0.2) {
//                                intent.putExtra("twoReviewHotels", twoReviewHotelLists.toString())
//                            } else if (ratingNow == 0.1) {
//                                intent.putExtra("oneReviewHotels", oneReviewHotelLists.toString())
//                            } else if (ratingNow == 0.0) {
//                                intent.putExtra("zeroReviewHotels", zeroReviewHotelLists.toString())
//                            }

