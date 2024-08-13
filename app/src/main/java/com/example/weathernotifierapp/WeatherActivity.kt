package com.example.weathernotifierapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.weathernotifierapp.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city = intent.getStringExtra("CITY_NAME") ?: "Unknown City"
        val temperature = "25°C"  // This would normally be retrieved from weather data

        binding.textViewCity.text = city
        binding.textViewTemperature.text = temperature

        // Request POST_NOTIFICATIONS permission for Android 13 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                showNotification("$temperature in $city", city)
            }
        } else {
            showNotification("$temperature in $city", city)
        }
    }

    // Handle the result of the permission request
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val city = intent.getStringExtra("CITY_NAME") ?: "Unknown City"
            val temperature = "25°C"  // This would normally be retrieved from weather data
            showNotification("$temperature in $city", city)
        } else {
            // Handle the case where the user denies the permission
        }
    }

    private fun showNotification(weatherInfo: String, cityName: String) {
        // Create and display a notification
        createNotificationChannel()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED) {
            val notification = NotificationCompat.Builder(this, "weather_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Weather Update")
                .setContentText(weatherInfo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            // Use city name's hash code as the notification ID to ensure uniqueness
            NotificationManagerCompat.from(this).notify(cityName.hashCode(), notification)
        }
    }

    // Create a notification channel for Android 8.0 and higher
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Weather Notification"
            val descriptionText = "Channel for weather notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("weather_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
