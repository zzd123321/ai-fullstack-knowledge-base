package com.example.bookmanager.service.impl;

import com.example.bookmanager.config.WeatherProperties;
import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.model.WeatherInfo;
import com.example.bookmanager.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherProperties weatherProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public WeatherInfo getWeather(String city) {

        try {
            // 构建URL
            URI uri = UriComponentsBuilder.fromHttpUrl(weatherProperties.getUrl())
                    .queryParam("q", city)
                    .queryParam("appid", weatherProperties.getApiKey())
                    .queryParam("units", "metric")
                    .queryParam("lang", "zh_cn")
                    .build()
                    .toUri();


            logger.info("调用天气API：{}", uri);

            // 发起HTTP GET请求，获取原始的JSON
            String json = restTemplate.getForObject(uri, String.class);

            logger.info("JSON结果：{}", json);

            // 通过objectMapper解析JSON
            JsonNode root = objectMapper.readTree(json);
            WeatherInfo weatherInfo = new WeatherInfo();
            weatherInfo.setCity(root.path("name").asText());
            weatherInfo.setTemperature(root.path("main").path("temp").asDouble());
            weatherInfo.setDescription(root.path("weather").get(0).path("description").asText());
            weatherInfo.setHumidity(root.path("main").path("humidity").asInt());
            weatherInfo.setWindSpeed(root.path("wind").path("speed").asDouble());

            return weatherInfo;

        } catch (HttpClientErrorException e) {
            // 处理4XX错误：如城市不存在
            logger.warn("天气API客户的错误：{}", e.getStatusCode());
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException("城市不存在：" + city);
            }
            throw new RuntimeException("外部API调用失败");
        } catch (ResourceAccessException e) {
            // 网络超时等
            logger.error("天气API连接超时", e);
            throw new RuntimeException("网络连接超时，请稍后重试");
        } catch (Exception e) {
            logger.error("调用添加API异常", e);
            throw new BusinessException("获取天气信息失败");
        }
    }
}
