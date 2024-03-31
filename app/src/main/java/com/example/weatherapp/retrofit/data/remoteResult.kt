package com.example.weatherapp.retrofit.data

data class remoteResult(
    var base: String,
    var clouds: Clouds,
    var cod: Int,
    var coord: Coord,
    var dt: Int,
    var id: Int,
    var main: Main,
    var name: String,
    var rain: Rain,
    var sys: Sys,
    var timezone: Int,
    var visibility: Int,
    var weather: List<Weather>,
    var wind: Wind
)