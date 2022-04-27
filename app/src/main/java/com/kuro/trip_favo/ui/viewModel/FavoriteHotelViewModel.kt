package com.kuro.trip_favo.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository
import com.kuro.trip_favo.ui.FavoriteListAdapter

class FavoriteHotelViewModel(private val favoriteHotelRepository: FavoriteHotelRepository) :
    ViewModel() {

    val allHotelData: LiveData<List<FavoriteHotel>> = favoriteHotelRepository.allHotelData

    val favoriteAdapter = FavoriteListAdapter()
    val selectedOrderPosition = MutableLiveData(0)

    var onsenData = 0
    lateinit var favoriteOrderLists: List<FavoriteHotel>


    fun selectedOrder(favoriteHotel: List<FavoriteHotel>) {

        if (onsenData == 0) {
            when (selectedOrderPosition.value) {
                0 -> favoriteOrderLists = favoriteHotel
                1 -> favoriteOrderLists = favoriteHotel.sortedByDescending { it.reviewAverage }
                2 -> favoriteOrderLists = favoriteHotel.sortedBy { it.hotelMinCharge }
                3 -> favoriteOrderLists = favoriteHotel.sortedByDescending { it.date }
                4 -> favoriteOrderLists = favoriteHotel.sortedBy { it.date }
            }
        } else if (onsenData == 1) {
            val onsenFavoriteHotelLists = favoriteHotel.filter { it.onsen == 1 }
            favoriteOrderLists = onsenFavoriteHotelLists

            when (selectedOrderPosition.value) {
                0 -> favoriteOrderLists = onsenFavoriteHotelLists
                1 -> favoriteOrderLists =
                    onsenFavoriteHotelLists.sortedByDescending { it.reviewAverage }
                2 -> favoriteOrderLists = onsenFavoriteHotelLists.sortedBy { it.hotelMinCharge }
                3 -> favoriteOrderLists = onsenFavoriteHotelLists.sortedByDescending { it.date }
                4 -> favoriteOrderLists = onsenFavoriteHotelLists.sortedBy { it.date }
            }
        }
        Log.d("favoriteOrderLists", favoriteOrderLists.toString())
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

