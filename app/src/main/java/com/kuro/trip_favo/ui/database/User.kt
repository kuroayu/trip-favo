package com.kuro.trip_favo.ui.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
//dataclassパターンとclassパターンの違いが知りたい
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "favodata")
    var hotelData: String
        )