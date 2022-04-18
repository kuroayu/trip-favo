package com.kuro.trip_favo.data.database

import android.app.Application
import com.kuro.trip_favo.data.repositry.FavoriteHotelRepository

class FavoriteApplication : Application() {

    val database by lazy { FavoriteHotelDatabase.getDatabase(this) }

    //favoriteHotelRepositoryのコンストラクタでrepositoryとdaoを紐づけているのにここでもまた？
    val repository by lazy { FavoriteHotelRepository(database.favoriteHotelDao()) }

}