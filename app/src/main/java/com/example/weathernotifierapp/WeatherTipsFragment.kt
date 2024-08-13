package com.example.weathernotifierapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weathernotifierapp.databinding.FragmentWeatherTipsBinding

class WeatherTipsFragment : Fragment(R.layout.fragment_weather_tips) {

    private var _binding: FragmentWeatherTipsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWeatherTipsBinding.bind(view)

        // Set text or perform actions with views
        binding.textViewTips.text = "Always carry an umbrella in unpredictable weather!"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
