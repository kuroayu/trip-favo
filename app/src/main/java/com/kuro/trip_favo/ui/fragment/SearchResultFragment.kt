package com.kuro.trip_favo.ui.fragment


import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuro.trip_favo.R
import com.kuro.trip_favo.data.api.HotelBasicInfo
import com.kuro.trip_favo.data.database.FavoriteApplication
import com.kuro.trip_favo.databinding.FragmentSearchResultBinding
import com.kuro.trip_favo.ui.SearchAdapter
import com.kuro.trip_favo.ui.viewModel.SearchResultViewModel
import com.kuro.trip_favo.ui.viewModel.SearchResultViewModelFactory


class SearchResultFragment : Fragment(R.layout.fragment_search_result) {


    private val viewModel: SearchResultViewModel by viewModels {
        val application = requireActivity().application as FavoriteApplication
        SearchResultViewModelFactory(
            application.favoriteHotelRepository,
            application.hotelRepository
        )
    }
    private val args: SearchResultFragmentArgs by navArgs()
    private val searchAdapter = SearchAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchResultBinding.bind(view)

        val linearLayoutManager = LinearLayoutManager(view.context)

        binding.searchRecyclerview.adapter = searchAdapter
        binding.searchRecyclerview.layoutManager = linearLayoutManager
        binding.searchRecyclerview.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )



        searchAdapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {

            override fun onItemClick(view: View, position: Int, data: HotelBasicInfo) {

                val hotelUrl = Uri.parse(data.hotelInformationUrl)
                val hotelIntent = Intent(Intent.ACTION_VIEW, hotelUrl)

                startActivity(hotelIntent)
            }


            override fun onFavoriteButtonClick(favoriteButton: View, data: HotelBasicInfo) {
                val animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.favorite_button_animation)
                favoriteButton.startAnimation(animation)
                if (!favoriteButton.isSelected) {
                    favoriteButton.isSelected = true
                    favoriteButton.apply {
                        setBackgroundResource(R.drawable.change_active_favorite_button)
                        (background as AnimationDrawable).start()
                    }
//                    viewModel.insert(data)

                } else if (favoriteButton.isSelected) {
                    favoriteButton.isSelected = false
                    favoriteButton.apply {
                        setBackgroundResource(R.drawable.change_nomal_favorite_button)
                        (background as AnimationDrawable).start()
                    }
                }
            }
        })


//                when (view) {
//                    favoriteButton -> {
//                        favoriteButton.startAnimation(animation)
//                        if (favoriteButton.isSelected || viewModel.hotelList.value?.get(1)?.isRegistered == true) {
//                            favoriteButton.isSelected = false
//                            favoriteButton.apply {
//                                setBackgroundResource(R.drawable.change_active_favorite_button)
//                                (background as AnimationDrawable).start()
//                            }
//                            Log.d("insert", data.toString())
//                        } else if (!favoriteButton.isSelected || viewModel.hotelList.value?.get(1)?.isRegistered == false) {
//                            favoriteButton.isSelected = true
//                            favoriteButton.apply {
//                                setBackgroundResource(R.drawable.change_nomal_favorite_button)
//                                (background as AnimationDrawable).start()
//                            }
//                            Log.d("delete", data.toString())
//                        }
//                    }
//                    else -> startActivity(hotelIntent)
//                }


//            override fun onItemInsertClick(data: HotelBasicInfo) {
//                viewModel.insert(data)
//                favoriteButton.startAnimation(animation)
//                favoriteButton.apply {
//                    setBackgroundResource(R.drawable.change_active_favorite_button)
//                    (background as AnimationDrawable).start()
//                }
//                Log.d("onItemInsertClick", data.toString())
//            }

//            override fun onItemDeleteClick(data: HotelBasicInfo) {
        //LiveDataじゃないとだめかも
//                val favoriteHotelData = viewModel.allHotelData
//                Log.d("onItemDeleteClick", "onItemDeleteClick")
//            }
//})


        viewModel.init(
            args.middleClassCode,
            args.smallClassCode,
            args.detailClassCode,
            args.squeezeCondition
        )

        viewModel.hotelList.observe(viewLifecycleOwner) {

            val ratingSearchHotelInfo =
                it.filter { it.hotelBasicInfo.reviewAverage >= args.selectedRating.toDouble() }
            searchAdapter.setHotelInfo(ratingSearchHotelInfo)
            searchAdapter.notifyDataSetChanged()
        }
    }


}




