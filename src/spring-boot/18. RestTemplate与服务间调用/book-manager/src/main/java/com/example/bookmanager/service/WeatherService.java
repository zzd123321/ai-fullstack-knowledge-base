package com.example.bookmanager.service;

import com.example.bookmanager.model.WeatherInfo;

public interface WeatherService {

    WeatherInfo getWeather(String city);
}
