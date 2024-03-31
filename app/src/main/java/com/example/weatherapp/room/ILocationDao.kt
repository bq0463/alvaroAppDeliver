package com.example.weatherapp.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ILocationDao {
    @Insert
    fun insertLocation(favLocationsEntity: FavLocationsEntity)

    @Query("SELECT * FROM FavLocationsEntity")
    fun getAllLocations(): List<FavLocationsEntity>

    @Query("SELECT COUNT(*) FROM FavLocationsEntity")
    fun getCount(): Int

    @Query("DELETE FROM FavLocationsEntity WHERE timestamp = :timestamp")
    fun deleteLocationByTimestamp(timestamp: Long)

}