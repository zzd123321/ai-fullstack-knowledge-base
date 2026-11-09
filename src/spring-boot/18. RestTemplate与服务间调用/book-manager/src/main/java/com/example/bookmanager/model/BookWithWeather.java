package com.example.bookmanager.model;

import lombok.Data;

@Data
public class BookWithWeather {
    private Book book;
    private WeatherInfo weatherInfo;

    public BookWithWeather(Book book, WeatherInfo weatherInfo) {
        this.book = book;
        this.weatherInfo = weatherInfo;
    }
}
