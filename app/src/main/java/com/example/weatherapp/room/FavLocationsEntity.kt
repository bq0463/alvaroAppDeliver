package com.example.weatherapp.room
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavLocationsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
)