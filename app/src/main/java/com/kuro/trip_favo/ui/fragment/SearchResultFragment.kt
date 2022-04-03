package com.kuro.trip_favo.ui.fragment

import RakutenHotelResult
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuro.trip_favo.R
import com.kuro.trip_favo.ui.SearchAdapter
import retrofit2.Call
import retrofit2.Response


class SearchResultFragment : Fragment() {

    private val args: SearchResultFragmentArgs by navArgs()
    private val adapter = SearchAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        val linearLayoutManager = LinearLayoutManager(view.context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerview)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )
        getHotel(args.selectedMiddleClassCode, args.selectedSmallClassCode)

        return view
    }

    fun getHotel(middleClassCode: String, smallClassCode: String) {
        SearchFragment().createService().getRakutenHotel(middleClassCode, smallClassCode)
            .enqueue(object : retrofit2.Callback<RakutenHotelResult> {
                override fun onFailure(call: Call<RakutenHotelResult>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<RakutenHotelResult>,
                    response: Response<RakutenHotelResult>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val hotelInfo = it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo } }
                            val selectedRating = args.selectedRating
                            if (selectedRating != -1) {
                                val ratingSearchHotelInfo =
                                    hotelInfo.filter { it.reviewAverage >= selectedRating.toDouble() }
                                adapter.setHotelInfo(ratingSearchHotelInfo)
                            } else {
                                adapter.setHotelInfo(hotelInfo)
                            }

                            adapter.notifyDataSetChanged()

                            //filter
                        }

                    }
                }
            }
            )
    }
}

