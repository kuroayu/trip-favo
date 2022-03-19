package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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

    fun getArea(view: View) {
        createService().getRakutenArea().enqueue(object : retrofit2.Callback<RakutenAreaResult> {
            override fun onFailure(call: Call<RakutenAreaResult>, t: Throwable) {
                Log.d("getarea", t.toString())

            }

            override fun onResponse(
                call: Call<RakutenAreaResult>,
                response: Response<RakutenAreaResult>
            ) {
                if (response.isSuccessful) {
                    Log.d("response", response.toString())

                    response.body()?.let {
                        Log.d("body", it.toString())

                        //リストの0番目でCodeが"japan"の値を取得
                        it.areaClasses.largeAreas.find { it.largeClass[0].largeClassCode == "japan" }
                            ?.let {
                                Log.d("large", it.toString())
                                //drop：largeClassからlargeClassName(国名)、middleAreas(都道府県情報)だけを取得した
                                //faltMap : 都道府県情報の都道府県名だけmap化したものを取得
                                val middleAreaLists = it.largeClass.drop(1).flatMap {
                                    it.middleAreas!!.map { it.middleClass[0].middleClassName }
                                }
                                Log.d("middleAreaLists", it.toString())

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


//
                            }
                    }
                } else {
                    Log.d("else", "else")
                }
            }

        })


    }

}