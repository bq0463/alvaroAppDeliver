package com.example.weatherapp.retrofit.data

data class Weather(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
)