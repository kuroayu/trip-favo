package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    private var selectedMiddleClassCode: String? = ""
    private var selectedSmallClassCode: String? = ""
    private var selectedRating: Int? = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        getArea(view)

        val searchBtn = view.findViewById<Button>(R.id.search_button)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingbar)

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            selectedRating = rating.toInt()
        }


        searchBtn.setOnClickListener {
            if (selectedMiddleClassCode != null && selectedSmallClassCode != null && selectedRating != null) {

                findNavController().navigate(
                    SearchFragmentDirections.actionSearchToSearchResultFragment(
                        selectedMiddleClassCode!!,
                        selectedSmallClassCode!!,
                        selectedRating!!
                    )
                )

            }
        }
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
                                    middleAreaLists,
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

                                            selectedMiddleClassCode = it.largeClass[1]
                                                .middleAreas!!
                                                .find { it.middleClass[0].middleClassName == selectedMiddlePosition.toString() }
                                                ?.middleClass?.get(0)?.middleClassCode

                                            val smallAreaLists = it.largeClass.drop(1).flatMap {
                                                it.middleAreas?.find { it.middleClass[0].middleClassName == selectedMiddlePosition.toString() }
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

                                            smallAreaSpinner.onItemSelectedListener =
                                                object : AdapterView.OnItemSelectedListener {
                                                    override fun onItemSelected(
                                                        parent: AdapterView<*>,
                                                        view: View?,
                                                        position: Int,
                                                        id: Long
                                                    ) {
                                                        val selectedSmallPosition =
                                                            parent.getItemAtPosition(position)
                                                        selectedSmallClassCode =
                                                            it.largeClass[1].middleAreas
                                                                ?.find { it.middleClass[0].middleClassName == selectedMiddlePosition.toString() }
                                                                ?.middleClass?.get(1)?.smallAreas
                                                                ?.find { it.smallClass[0].smallClassName == selectedSmallPosition.toString() }
                                                                ?.smallClass?.get(0)?.smallClassCode
                                                    }

                                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                                    }
                                                }
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
}
