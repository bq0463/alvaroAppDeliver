package com.example.weatherapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavLocationsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): ILocationDao
}