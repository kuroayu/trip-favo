package com.kuro.trip_favo.ui.viewModel

import HotelBasicInfo
import androidx.lifecycle.*
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


    //リストバラせない
    //ホテルナンバーが一致しているものは取れたけど、こんなことしなくても出来そうな
    //ImageViewをdatabindingして、データベースに保存されているものは塗りつぶし、新規はぬりつぶしなしアイコン
//    fun checkedSameData(basicInfo: List<HotelBasicInfo>) {
//        viewModelScope.launch {
//
//
//            val hotelData =
//                favoriteHotelRepository.getAllHotelData().map { it.hotelNumber }
//            Log.d(
//                    "hotelData",
//                    hotelData.toString()
//                )
//
//
//            val searchData = basicInfo.map { it.hotelNo }
//            Log.d("searchData", searchData.toString())
//
//
//            searchData.forEach {
//                val list = it
//                val sameData = hotelData.filter { it == list }
//                Log.d("sameData", sameData.toString())
//            }
//        }
//    }


    fun insert(data: HotelBasicInfo) {
        viewModelScope.launch {

            val date = System.currentTimeMillis()
            val favoriteHotel = data.toFavoriteHotel(date, onsen)

            favoriteHotelRepository.insert(favoriteHotel)
        }
    }

    data class RenderListItem(
        val hotelBasicInfo: HotelBasicInfo,
        val isRegistered: Boolean
    )
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

