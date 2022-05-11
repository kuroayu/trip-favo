package com.kuro.trip_favo.ui.viewModel


import android.util.Log
import androidx.lifecycle.*
import com.kuro.trip_favo.data.api.HotelBasicInfo
import com.kuro.trip_favo.data.database.FavoriteHotel
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


//    val allHotelData = viewModelScope.launch { favoriteHotelRepository.getAllHotelData() }
//    val allHotelData: LiveData<List<FavoriteHotel>> = MutableLiveData()

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

    fun insert(data: HotelBasicInfo) {
        viewModelScope.launch {

            val date = System.currentTimeMillis()
            val favoriteHotel = data.toFavoriteHotel(date, onsen)
            Log.d("favoriteHotel", favoriteHotel.toString())

            favoriteHotelRepository.insert(favoriteHotel)
        }
    }

    //    追記
    fun delete(data: FavoriteHotel) {
        viewModelScope.launch {
            favoriteHotelRepository.delete(data)
        }
    }

    data class RenderListItem(
        val hotelBasicInfo: HotelBasicInfo,
        val isRegistered: Boolean
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

