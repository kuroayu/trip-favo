package com.kuro.trip_favo.ui.fragment

import HotelBasicInfo
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
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
import com.kuro.trip_favo.data.database.FavoriteApplication
import com.kuro.trip_favo.ui.SearchAdapter
import com.kuro.trip_favo.ui.viewModel.SearchResultViewModel
import com.kuro.trip_favo.ui.viewModel.SearchResultViewModelFactory


class SearchResultFragment : Fragment() {

    private val viewModel: SearchResultViewModel by viewModels {
        val application = requireActivity().application as FavoriteApplication
        SearchResultViewModelFactory(
            application.favoriteHotelRepository,
            application.hotelRepository
        )
    }
    private val args: SearchResultFragmentArgs by navArgs()
    private val searchAdapter = SearchAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        val linearLayoutManager = LinearLayoutManager(view.context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerview)

        recyclerView.adapter = searchAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )
        searchAdapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, data: HotelBasicInfo) {

                val hotelUrl = Uri.parse(data.hotelInformationUrl)
                val hotelIntent = Intent(Intent.ACTION_VIEW, hotelUrl)
                val animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.favorite_button_animation)
                val favoriteButton = view.findViewById<ImageView>(R.id.favorite_button)


                //binding？
                //mutableLiveDataにしておく？


                when (view) {
                    favoriteButton -> {
                        favoriteButton.startAnimation(animation)

                        if (!favoriteButton.isSelected) {
                            favoriteButton.isSelected = true
                            favoriteButton.apply {
                                setBackgroundResource(R.drawable.change_active_favorite_button)
                                (background as AnimationDrawable).start()
                            }
                        } else if (favoriteButton.isSelected) {
                            favoriteButton.isSelected = false
                            favoriteButton.apply {
                                setBackgroundResource(R.drawable.change_nomal_favorite_button)
                                (background as AnimationDrawable).start()
                            }
                        }
                        viewModel.insert(data)
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
            searchAdapter.setHotelInfo(ratingSearchHotelInfo)
            searchAdapter.notifyDataSetChanged()
        }

        return view
    }
}

