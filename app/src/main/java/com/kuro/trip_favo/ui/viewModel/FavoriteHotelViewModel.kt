package com.kuro.trip_favo.ui.viewModel

import androidx.lifecycle.*
import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository
import kotlinx.coroutines.launch

class FavoriteHotelViewModel(private val favoriteHotelRepository: FavoriteHotelRepository) :
    ViewModel() {

    private val result = mutableSetOf<FavoriteHotel>()
    private val _allHotelData: MutableLiveData<List<FavoriteHotel>> = MutableLiveData()
    val allHotelData: LiveData<List<FavoriteHotel>> = _allHotelData

    val selectedOrderPosition = MutableLiveData(0)

    val onsenData = MutableLiveData<Boolean>()

    val searchWord = MutableLiveData<String>()

    fun init() {
        viewModelScope.launch {
            runCatching {
                result.addAll(favoriteHotelRepository.getAllHotelData())
                _allHotelData.value = result.toList()
            }.onFailure {

            }
        }
    }


    fun selectedOrder() {
        //検索された言葉があればそれを返す、orEmptyで空なら自分自身を返す、この場合はitに当たるFavoriteHotelが返ってくる？
        _allHotelData.value = result.filter {
            it.hotelName.contains(searchWord.value.orEmpty()) ||
                    it.address1.contains(searchWord.value.orEmpty()) ||
                    it.address2.contains(searchWord.value.orEmpty())
        }.run {
            if (onsenData.value == true) {
                filter { it.onsen == 1 }
            } else this
        }.run {
            when (selectedOrderPosition.value) {
                0 -> this
                1 -> this.sortedByDescending { it.reviewAverage }
                2 -> this.sortedBy { it.hotelMinCharge }
                3 -> this.sortedByDescending { it.date }
                4 -> this.sortedBy { it.date }

                else -> this
            }
        }
    }
}


class FavoriteHotelViewModelFactory(
    private val favoriteHotelRepository: FavoriteHotelRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteHotelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteHotelViewModel(favoriteHotelRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

