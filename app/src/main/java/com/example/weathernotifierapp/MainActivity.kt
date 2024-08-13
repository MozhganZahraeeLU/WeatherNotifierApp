package com.example.weathernotifierapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.weathernotifierapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle button click to start WeatherActivity
        binding.buttonGetWeather.setOnClickListener {
            val cityName = binding.editTextCityName.text.toString()
            val intent = Intent(this, WeatherActivity::class.java)
            intent.putExtra("CITY_NAME", cityName)
            startActivity(intent)
        }

        // Load the WeatherTipsFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, WeatherTipsFragment())
            .commit()
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle the settings action
                // For now, just a placeholder; you can add settings activity here
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
