package com.kuro.trip_favo.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository

class FavoriteSearchViewModel(private val favoriteHotelRepository: FavoriteHotelRepository) :
    ViewModel() {

    val allHotelData: LiveData<List<FavoriteHotel>> = favoriteHotelRepository.allHotelData

    val selectedOrderPosition = MutableLiveData(0)

    lateinit var favoriteOrderLists: List<FavoriteHotel>


    //遷移先でやるやれない
    fun selectedOrder(favoriteHotel: List<FavoriteHotel>) {
        if (selectedOrderPosition.value == 1) {
            favoriteOrderLists =
                favoriteHotel.sortedBy { it.reviewAverage }
        }
    }

}

class FavoriteSearchViewModelFactory(
    private val favoriteHotelRepository: FavoriteHotelRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteSearchViewModel(favoriteHotelRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}