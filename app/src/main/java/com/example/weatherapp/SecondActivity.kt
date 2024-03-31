package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.retrofit.RetrofitOpenWeatherAPI
import com.example.weatherapp.retrofit.data.Rain
import com.example.weatherapp.retrofit.data.remoteResult
import com.example.weatherapp.retrofit.openWeatherMapAPI
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat

class SecondActivity : AppCompatActivity(){

    private val TAG = "btaSecondActivity"
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    var temperature: Int = 0
    var tempMax: Int = 0
    var windSpeed: Double?=null
    var coordLat: Double?=null
    var coordLong: Double?=null
    var rain: String? =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_second)
        Log.d(TAG, "onCreate: The activity is being created.")

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val temperatureTextView: TextView = findViewById(R.id.temperatureTextView)
        val tempMaxTextView: TextView = findViewById(R.id.TempMaxTextView)
        val windSpeedTextView: TextView = findViewById(R.id.windSpeedTextView)
        val coordTextView: TextView = findViewById(R.id.coordTextView)
        val rainTextView: TextView = findViewById(R.id.rainTextView)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Time"
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val location: Location? = intent.getParcelableExtra("location")
        val service=RetrofitOpenWeatherAPI.makeRetrofitOpenWeatherAPI()
        var result: remoteResult? =null
        lifecycleScope.launch {
            if (location != null) {
                result=service.getWeather(location.latitude,location.longitude,"7c898580f323d9df5483f809cc937e69")
                coordLat=result!!.coord.lat
                coordLong=result!!.coord.lon
                windSpeed=result!!.wind.speed
                temperature = (result!!.main.temp-273.15).toInt()
                tempMax=(result!!.main.temp_max-273.15).toInt()
                rain=result!!.weather[0].main
                temperatureTextView.text = "Temp: $temperature"+"ºC"
                tempMaxTextView.text = "Max Temp: $tempMax"+"ºC"
                windSpeedTextView.text = "Wind Speed: $windSpeed"
                coordTextView.text = "Coordinates: $coordLat"+", $coordLong"
                rainTextView.text="Time : $rain"
            }
        }



// Mostrar los valores en los TextViews



        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            val currentActivity = this::class.java.simpleName
            when (item.itemId) {
                R.id.nav_home-> if (currentActivity != MainActivity::class.java.simpleName) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.nav_time -> if (currentActivity != SecondActivity::class.java.simpleName) {
                    startActivity(Intent(this, SecondActivity::class.java))
                }
                R.id.nav_dress -> if (currentActivity != Third_Activity::class.java.simpleName) {

                    val intent = Intent(this@SecondActivity,Third_Activity::class.java).apply{
                        intent.putExtra("temp",temperature)
                        intent.putExtra("MaxTemp",tempMax)
                        intent.putExtra("windSpeed",windSpeed)
                        intent.putExtra("rain",rain)
                    }
                    startActivity(intent)
                }
                R.id.nav_logout->{
                    logout()
                    true
                }
            }
            true
        }



    }

    private fun getUserIdentifier(): String? {
        val sharedPreferences = this.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userIdentifier", null)
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // Restart activity after finishing
                val intent = Intent(this, MainActivity::class.java)
                // Clean back stack so that user cannot retake activity after logout
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
    }

}