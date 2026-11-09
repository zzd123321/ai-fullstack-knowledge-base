package com.example.bookmanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "天气信息")
public class WeatherInfo {
    private String city;
    private Double temperature;
    private String description;
    private Integer humidity;
    private Double windSpeed;
}
