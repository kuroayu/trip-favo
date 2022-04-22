package com.kuro.trip_favo.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuro.trip_favo.data.database.FavoriteHotel

class FavoriteSearchViewModel : ViewModel() {

    lateinit var favoriteHotelList: List<FavoriteHotel>

    val selectedOrderPosition: MutableLiveData<Int> = MutableLiveData(0)

}