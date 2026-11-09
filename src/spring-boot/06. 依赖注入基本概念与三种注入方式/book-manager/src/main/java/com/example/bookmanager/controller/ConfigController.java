package com.example.bookmanager.controller;

import com.example.bookmanager.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private AppProperties appProperties;

    @GetMapping
    public Map<String, Object> getAppConfig() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", appProperties.getName());
        result.put("version", appProperties.getVersion());
        result.put("author", appProperties.getAuthor());
        result.put("features", appProperties.getFeatures());
        result.put("contact", appProperties.getContact());
        return result;
    }


    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.welcome-message:默认欢迎语}")  // 冒号后面是默认值
    private String welcomeMessage;

    @GetMapping("/info")
    public String getAppInfo() {
        return String.format("应用: %s, 版本: %s", appName, appVersion);
    }

    @GetMapping("/welcome")
    public String getWelcome() {
        return welcomeMessage;
    }
}