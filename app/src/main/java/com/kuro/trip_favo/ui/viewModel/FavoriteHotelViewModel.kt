package com.kuro.trip_favo.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuro.trip_favo.data.database.FavoriteHotel
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository

class FavoriteHotelViewModel(private val favoriteHotelRepository: FavoriteHotelRepository) :
    ViewModel() {

    val allHotelData: LiveData<List<FavoriteHotel>> = favoriteHotelRepository.allHotelData

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