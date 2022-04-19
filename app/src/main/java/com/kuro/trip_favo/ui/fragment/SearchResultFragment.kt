package com.kuro.trip_favo.ui.fragment

import HotelBasicInfo
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuro.trip_favo.R
import com.kuro.trip_favo.ui.SearchAdapter
import com.kuro.trip_favo.ui.viewModel.SearchResultViewModel


class SearchResultFragment : Fragment() {

    private val viewModel: SearchResultViewModel by viewModels()
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
        adapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, data: HotelBasicInfo) {

                val hotelUrl = Uri.parse(data.hotelInformationUrl)
                val hotelIntent = Intent(Intent.ACTION_VIEW, hotelUrl)
                val animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.favorite_button_animation)

                val favoriteButton = view.findViewById<ImageView>(R.id.favorite_button)

                when (view) {
                    favoriteButton -> {
                        viewModel.favoriteButton(favoriteButton)
                        favoriteButton.startAnimation(animation)
                        viewModel.favoriteAnimation.start()
                    }
                    else -> startActivity(hotelIntent)
                }
            }
        })

        viewModel.init(
            args.middleClassCode,
            args.smallClassCode,
            args.detailClassCode,
            args.squeezeCondition
        )

        viewModel.hotelList.observe(viewLifecycleOwner) {
            val ratingSearchHotelInfo =
                it.filter { it.reviewAverage >= args.selectedRating.toDouble() }
            adapter.setHotelInfo(ratingSearchHotelInfo)
            adapter.notifyDataSetChanged()
        }

        return view
    }
}

