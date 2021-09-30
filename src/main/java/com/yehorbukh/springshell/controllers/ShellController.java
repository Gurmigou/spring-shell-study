package com.yehorbukh.springshell.controllers;

import com.yehorbukh.springshell.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;

@ShellComponent
public class ShellController {
    private final WeatherService weatherService;

    @Autowired
    public ShellController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ShellMethod(key = "weather in",
                 value = "returns current weather of the selected region")
    public String currentWeatherInRegion(String region) throws IOException, InterruptedException {
        return weatherService.getCurrentDescription(region);
    }

    @ShellMethod(key = "do i need umbrella in",
                 value = "returns a message which tells if the user needs an umbrella now")
    public String doINeedUmbrella(String region) throws IOException, InterruptedException {
        boolean rains = weatherService.isRainNow(region);
        return rains ? "Yes, it's raining now" : "No, leave your umbrella at home";
    }
}
