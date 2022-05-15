package com.kuro.trip_favo.ui.viewModel


import androidx.lifecycle.*
import com.kuro.trip_favo.data.api.HotelBasicInfo
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository
import com.kuro.trip_favo.data.repositry.HotelRepository
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val favoriteHotelRepository: FavoriteHotelRepository,
    private val hotelRepository: HotelRepository
) :
    ViewModel() {


    private val _hotelResult: MutableLiveData<List<RenderListItem>> = MutableLiveData()

    val hotelList: LiveData<List<RenderListItem>> = _hotelResult

    private var onsen = 0

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
                val result = hotelRepository.getHotel(
                    middleClassCode,
                    smallClassCode,
                    detailClassCode,
                    squeezeCondition
                ).body()!!.hotels.flatMap { it.hotel.map { it.hotelBasicInfo } }
                val registeredResult = favoriteHotelRepository.getAllHotelData()
                result.map { hotelBasicInfo ->
                    RenderListItem(
                        hotelBasicInfo = hotelBasicInfo,
                        isRegistered = registeredResult.any { hotelBasicInfo.hotelNo == it.hotelNumber }
                            .takeIf { it }
                    )
                }
            }.onSuccess {
                val resultSqueezeCondition = squeezeCondition.isEmpty()
                onsen = when (resultSqueezeCondition) {
                    true -> 0
                    false -> 1
                }
                _hotelResult.value = it
            }.onFailure {
                _isError.value = true
            }
        }
    }

    fun onFavoriteButtonClick(renderListItem: RenderListItem) {
        viewModelScope.launch {
            val date = System.currentTimeMillis()
            val favoriteHotel = renderListItem.hotelBasicInfo.toFavoriteHotel(date, onsen)
            if (renderListItem.isRegistered == true) {
                favoriteHotelRepository.delete(favoriteHotel)
            } else {
                favoriteHotelRepository.insert(favoriteHotel)
            }
            _hotelResult.value = _hotelResult.value?.map {
                if (it.hotelBasicInfo.hotelNo == renderListItem.hotelBasicInfo.hotelNo) {
                    it.copy(isRegistered = !(it.isRegistered ?: false))
                } else {
                    it
                }
            }
        }
    }

    data class RenderListItem(
        val hotelBasicInfo: HotelBasicInfo,
        val isRegistered: Boolean?
    ) {
        val price: String
            get() = "${hotelBasicInfo.hotelMinCharge}円"

        val address: String
            get() = "${hotelBasicInfo.address1}${hotelBasicInfo.address2}"

        val rating: Float
            get() = hotelBasicInfo.reviewAverage.toFloat()
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

