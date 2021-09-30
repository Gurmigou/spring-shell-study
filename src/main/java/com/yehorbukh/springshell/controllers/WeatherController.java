package com.yehorbukh.springshell.controllers;

import com.yehorbukh.springshell.exceptions.RegionNotFoundException;
import com.yehorbukh.springshell.models.Weather;
import com.yehorbukh.springshell.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{region}")
    public ResponseEntity<?> getWeatherInRegion(@PathVariable String region) {
        try {
            Weather weather = weatherService.getWeather(region);
            return ResponseEntity.ok(weather);
        } catch (IOException | InterruptedException | RegionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rain/{region}")
    public ResponseEntity<?> isRainingInRegion(@PathVariable String region) {
        try {
            Weather weather = weatherService.getWeather(region);
            boolean isRaining = weatherService.isRain(weather);
            return ResponseEntity.ok(isRaining);
        } catch (IOException | InterruptedException | RegionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
