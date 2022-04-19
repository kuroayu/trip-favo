package com.kuro.trip_favo.ui.viewModel

import HotelBasicInfo
import RakutenHotelResult
import android.graphics.drawable.AnimationDrawable
import android.view.View
import androidx.lifecycle.*
import com.kuro.trip_favo.R
import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository
import com.kuro.trip_favo.data.repositry.HotelRepository
import kotlinx.coroutines.launch

class SearchResultViewModel(private val hotelRepository: HotelRepository = HotelRepository()) :
    ViewModel() {

    lateinit var favoriteHotelRepository: FavoriteHotelRepository
    private val _hotelResult: MutableLiveData<RakutenHotelResult> = MutableLiveData()

    val hotelList: LiveData<List<HotelBasicInfo>> = _hotelResult.map {
        it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo } }
    }

    private var onsen = 2

    private var _favoriteAnimation = AnimationDrawable()
    val favoriteAnimation = _favoriteAnimation

    private val _isError: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError


    fun init(
        middleClassCode: String,
        smallClassCode: String,
        detailClassCode: String,
        squeezeCondition: String
    ) {
        viewModelScope.launch {
            runCatching {
                hotelRepository.getHotel(
                    middleClassCode,
                    smallClassCode,
                    detailClassCode,
                    squeezeCondition
                )
            }.onSuccess {
                if (it.isSuccessful) {
                    _hotelResult.value = it.body()
                    val result = squeezeCondition.isEmpty()
                    onsen = when (result) {
                        true -> 1
                        false -> 0
                    }
                } else {
                    _isError.value = true
                }
            }.onFailure {
                _isError.value = true
            }
        }
    }

    fun favoriteButton(favoriteButton: View) {
        
        if (!favoriteButton.isSelected) {
            favoriteButton.isSelected = true
            favoriteButton.apply {
                setBackgroundResource(R.drawable.change_active_favorite_button)
                _favoriteAnimation = background as AnimationDrawable
            }
        } else if (favoriteButton.isSelected) {
            favoriteButton.isSelected = false
            favoriteButton.apply {
                setBackgroundResource(R.drawable.change_nomal_favorite_button)
                _favoriteAnimation = background as AnimationDrawable
            }

        }
    }


    fun insert(hotelData: FavoriteHotel) = viewModelScope.launch {
        favoriteHotelRepository.insert(hotelData)
    }

    fun favoriteHotelData() {
        onsen
    }


}