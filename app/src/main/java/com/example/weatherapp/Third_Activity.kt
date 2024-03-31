package com.example.weatherapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.firebase.ui.auth.AuthUI

class Third_Activity : AppCompatActivity(){
    private val TAG = "btaThirdActivity"
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        Log.d(TAG, "onCreate: The activity is being created.")

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val temperature: Double = intent.getDoubleExtra("temp", 0.0)
        val Maxtemp: Double = intent.getDoubleExtra("MaxTemp", 0.0)
        val windSpeed: Double = intent.getDoubleExtra("windSpeed", 0.0)
        val rain: String? = intent.getStringExtra("rain")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Dress"
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val textView: TextView = findViewById(R.id.textView1)



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
                    startActivity(Intent(this, Third_Activity::class.java))
                }
                R.id.nav_logout->{
                    logout()
                    true
                }
            }
            true
        }

        val message = when {
            true -> {
                when {
                    rain=="Thunderstorm" ->{
                        "Be careful with the thunderstorm"
                    }
                    Maxtemp < 11 && windSpeed > 7.0 && (rain=="Rain" || rain=="Drizzle" || rain=="Fog" || rain=="Showers" || rain=="Snow") -> {
                        "I recommend you to wear a good windbreaker," +
                                " along with longs , gloves and an umbrella"
                    }
                    Maxtemp < 11 && windSpeed > 7.0 -> {
                        "I recommend you to wear a good windbreaker," +
                                " along with longs and gloves if possible"
                    }
                    Maxtemp < 11 && windSpeed <= 7.0 && (rain=="Rain" || rain=="Drizzle" || rain=="Fog" || rain=="Showers" || rain=="Snow")-> {
                        "I recommend you to wear a good jacket along with another outerwear, longs and gloves and an umbrella"
                    }
                    Maxtemp < 11 && windSpeed <= 7.0 -> {
                        "I recommend you to wear a good jacket along with another outerwear, longs and gloves"
                    }
                    Maxtemp in 11.0..25.0 && windSpeed <= 7.0 && (rain=="Rain" || rain=="Drizzle" || rain=="Fog" || rain=="Showers" || rain=="Snow")-> {
                        "I recommend you to wear longs/shorts/skirt along with a T-shirt and/or another outerwear or a dress and an umbrella"
                    }
                    Maxtemp in 11.0..25.0 && windSpeed <= 7.0 -> {
                        "I recommend you to wear longs/shorts/skirt along with a T-shirt and/or another outerwear or a dress"
                    }
                    else -> {
                        "I recommend you to wear shorts/skirt and a t-shirt or a dress"
                    }
                }
            }
            else -> {
                "Unable to provide recommendation"
            }
        }
        textView.text=message

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