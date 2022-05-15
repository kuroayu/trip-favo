package com.kuro.trip_favo.ui.fragment


import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(
            args.middleClassCode,
            args.smallClassCode,
            args.detailClassCode,
            args.squeezeCondition
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchAdapter = SearchAdapter(viewLifecycleOwner)

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
        binding.searchRecyclerview.itemAnimator = null



        searchAdapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {

            override fun onItemClick(view: View, position: Int, data: HotelBasicInfo) {

                val hotelUrl = Uri.parse(data.hotelInformationUrl)
                val hotelIntent = Intent(Intent.ACTION_VIEW, hotelUrl)

                startActivity(hotelIntent)
            }

            override fun onFavoriteButtonClick(
                favoriteButton: View,
                renderListItem: SearchResultViewModel.RenderListItem
            ) {
                viewModel.onFavoriteButtonClick(renderListItem)
            }
        })

        viewModel.hotelList.observe(viewLifecycleOwner) {
            val ratingSearchHotelInfo =
                it.filter { it.hotelBasicInfo.reviewAverage >= args.selectedRating.toDouble() }
            searchAdapter.submitList(ratingSearchHotelInfo)
        }
    }
}


@BindingAdapter("isRegistered")
fun ImageView.setRegistered(isRegistered: Boolean?) {
    isRegistered ?: setImageResource(R.drawable.ic_baseline_favorite_border_24)
    val animation =
        AnimationUtils.loadAnimation(context, R.anim.favorite_button_animation)
    startAnimation(animation)
    if (isRegistered == true) {
        setBackgroundResource(R.drawable.change_active_favorite_button)
        (background as AnimationDrawable).start()
    } else {
        setBackgroundResource(R.drawable.change_nomal_favorite_button)
        (background as AnimationDrawable).start()
    }
}



