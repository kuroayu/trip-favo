package com.kuro.trip_favo.ui.viewModel

import HotelBasicInfo
import RakutenHotelResult
import androidx.lifecycle.*
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository
import com.kuro.trip_favo.data.repositry.HotelRepository
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val favoriteHotelRepository: FavoriteHotelRepository,
    private val hotelRepository: HotelRepository
) :
    ViewModel() {


    private val _hotelResult: MutableLiveData<RakutenHotelResult> = MutableLiveData()

    val hotelList: LiveData<List<HotelBasicInfo>> = _hotelResult.map {
        it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo } }
    }

    private var onsen = 2

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


    fun insert(data: HotelBasicInfo) {
        viewModelScope.launch {

            val date = System.currentTimeMillis()
            val favoriteHotel = data.toFavoriteHotel(date, onsen)

            favoriteHotelRepository.insert(favoriteHotel)
        }
    }
}

class SearchResultViewModelFactory(
    private val favoriteHotelRepository: FavoriteHotelRepository,
    private val hotelRepository: HotelRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchResultViewModel(favoriteHotelRepository, hotelRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}