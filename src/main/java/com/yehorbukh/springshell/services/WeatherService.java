package com.yehorbukh.springshell.services;

import com.yehorbukh.springshell.exceptions.RegionNotFoundException;
import com.yehorbukh.springshell.models.Weather;
import com.yehorbukh.springshell.models.WeatherType;
import com.yehorbukh.springshell.repositories.WeatherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {
    private final WeatherRepo weatherRepo;

    @Autowired
    public WeatherService(WeatherRepo weatherRepo) {
        this.weatherRepo = weatherRepo;
    }

    public boolean isRain(Weather weather) {
        return weather.getWeatherType() == WeatherType.RAIN;
    }

    public boolean isRainNow(String region) throws IOException, InterruptedException, RegionNotFoundException {
        var weather = weatherRepo.currentWeather(region);
        return isRain(weather);
    }

    public String getCurrentWeatherDescription(String region) throws IOException, InterruptedException, RegionNotFoundException {
        var weather = weatherRepo.currentWeather(region);
        return weather.getDescription();
    }

    public Weather getWeather(String region) throws IOException, InterruptedException, RegionNotFoundException {
        return weatherRepo.currentWeather(region);
    }
}
