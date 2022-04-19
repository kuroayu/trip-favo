package com.kuro.trip_favo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteHotel::class], version = 1, exportSchema = false)
abstract class FavoriteHotelDatabase : RoomDatabase() {
    abstract fun favoriteHotelDao(): FavoriteHotelDao


    companion object {
        private var INSTANCE: FavoriteHotelDatabase? = null

        fun getDatabase(
            context: Context
        ): FavoriteHotelDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteHotelDatabase::class.java,
                        "FavoriteHotel.db"
                    )
                        .build()
                }
                return INSTANCE!!

            }
        }
    }
}
