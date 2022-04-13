package com.kuro.trip_favo.ui.viewModel

import HotelBasicInfo
import RakutenHotelResult
import androidx.lifecycle.*
import com.kuro.trip_favo.data.repositry.HotelRepository
import kotlinx.coroutines.launch

class SearchResultViewModel(private val hotelRepository: HotelRepository = HotelRepository()) :
    ViewModel() {

    private val _hotelResult: MutableLiveData<RakutenHotelResult> = MutableLiveData()

    val hotelList: LiveData<List<HotelBasicInfo>> = _hotelResult.map {
        it.hotels.flatMap { it.hotel.map { it.hotelBasicInfo } }
    }

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
                } else {
                    _isError.value = true
                }
            }.onFailure {
                _isError.value = true
            }
        }
    }


}