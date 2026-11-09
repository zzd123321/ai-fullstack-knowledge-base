package com.example.bookmanager.controller;

import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.WeatherInfo;
import com.example.bookmanager.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "外部服务", description = "调用第三方服务接口")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    @Operation(summary = "查询城市天气", description = "调用第三方天气 API 获取实时天气")
    public Result<WeatherInfo> getWeather(
            @Parameter(description = "城市名称（拼音）", required = true, example = "beijing")
            @RequestParam String city) {
        WeatherInfo weather = weatherService.getWeather(city);
        return Result.success(weather);
    }
}
