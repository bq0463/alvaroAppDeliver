package com.example.weatherapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.weatherapp.room.AppDatabase
import com.example.weatherapp.room.FavLocationsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

class MainActivity : AppCompatActivity() ,LocationListener{
    private val TAG = "MainActivity"
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var locationManager: LocationManager
    private var latestLocation: Location? = null
    private val locationPermissionCode = 2

   // lateinit var database: AppDatabase
    companion object {
        private const val RC_SIGN_IN = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: The activity is being created.")

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Home"
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            val currentActivity = this::class.java.simpleName
            when (item.itemId) {
                R.id.nav_home-> {if (currentActivity != MainActivity::class.java.simpleName) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                    true
                }

                R.id.nav_time -> {if (currentActivity != SecondActivity::class.java.simpleName) {
                    if (latestLocation != null) {
                        val intent = Intent(this, SecondActivity::class.java)
                        val bundle = Bundle()
                        bundle.putParcelable("location", latestLocation)
                        intent.putExtra("locationBundle", bundle)
                        intent.putExtra("location",latestLocation )
                        startActivity(intent)
                    }else{
                        Log.e(TAG, "Location not set yet.")
                        startActivity(Intent(this, SecondActivity::class.java))
                    }
                    true

                }
                    true
                }
                R.id.nav_dress ->{ if (currentActivity != Third_Activity::class.java.simpleName) {
                    startActivity(Intent(this, Third_Activity::class.java))
                }
                    true
                }
                R.id.nav_logout->{
                    logout()
                    true
                }

                else -> false
            }

        }
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                locationPermissionCode
            )
        } else {
            // The location is updated every 5000 milliseconds (or 5 seconds) and/or if the device moves more than 5 meters,
            // whichever happens first
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        }
        val userIdentifier = getUserIdentifier()
        if (userIdentifier == null) {
            askForUserIdentifier()
        } else {
            Toast.makeText(this, "User ID: $userIdentifier", Toast.LENGTH_LONG).show()
        }
        launchSignInFlow()
    }


    override fun onLocationChanged(location: Location) {
        latestLocation = location
        val textView: TextView = findViewById(R.id.mainTextView1)
        textView.text = "\uD83D\uDCCD Latitude: [${location.latitude}], Longitude: [${location.longitude}], UserId: [${getUserIdentifier()}]"
        Toast.makeText(this, "Coordinates update! [${location.latitude}][${location.longitude}]", Toast.LENGTH_LONG).show()

    }




    private fun askForUserIdentifier() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Enter User Identifier")
            .setIcon(R.mipmap.ic_launcher)
            .setView(input)
            .setPositiveButton("Save") { dialog, which ->
                val userInput = input.text.toString()
                if (userInput.isNotBlank()) {
                    saveUserIdentifier(userInput)
                    Toast.makeText(this, "User ID saved: $userInput", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "User ID cannot be blank", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveUserIdentifier(userIdentifier: String) {
        val sharedPreferences = this.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("userIdentifier", userIdentifier)
            apply()
        }
    }

    private fun getUserIdentifier(): String? {
        val sharedPreferences = this.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userIdentifier", null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
// user login succeeded
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, R.string.signed_in, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onActivityResult " + getString(R.string.signed_in));
            } else {
// user login failed
                Log.e(TAG, "Error starting auth session: ${response?.error?.errorCode}")
                Toast.makeText(this, R.string.signed_cancelled, Toast.LENGTH_SHORT).show();
                finish()
            }
        }
    }
    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
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
    private fun updateUIWithUsername() {
        val user = FirebaseAuth.getInstance().currentUser
        val userNameTextView: TextView = findViewById(R.id.userNameTextView)
        user?.let {
            val name = user.displayName ?: "No Name"
            userNameTextView.text ="Welcome" +"\uD83E\uDD35\u200D♂\uFE0F " + name
        }
    }
    override fun onResume() {
        super.onResume()
        updateUIWithUsername()
    }


}