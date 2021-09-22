package com.yehorbukh.springshell.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {
    private static final String KEY = "5a389c49c495639d5153758bca6606ac";

    private static final String apiURL = "https://api.openweathermap.org/data/2.5/" +
            "weather?q={REGION}&appid=" + KEY;

    private String insertRegionIntoApiUrl(String region) {
        return apiURL.replace("{REGION}", region);
    }

    private String json(String region) throws IOException, InterruptedException {
        // get an api url with correct region name
        String urlApiWithRegion = insertRegionIntoApiUrl(region);

        var client = HttpClient.newBuilder().build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(urlApiWithRegion))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private boolean isRain(int value) {
        return value >= 500 && value <= 531 || value >= 300 && value <= 321;
    }

    public String currentWeather(String region) throws IOException, InterruptedException {
        var json = new JSONObject(json(region));
        return json.getJSONArray("weather").getJSONObject(0).getString("description");
    }

    public boolean isItRainingNow(String region) throws IOException, InterruptedException {
        var json = new JSONObject(json(region));
        int weatherType = json.getJSONArray("weather")
                              .getJSONObject(0)
                              .getInt("id");
        return isRain(weatherType);
    }
}
