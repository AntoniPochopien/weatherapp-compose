package com.example.weatherapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_tbl")
data class UnitSystem(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val unit: String
)
