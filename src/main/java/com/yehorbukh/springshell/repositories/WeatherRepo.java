package com.yehorbukh.springshell.repositories;

import com.yehorbukh.springshell.exceptions.RegionNotFoundException;
import com.yehorbukh.springshell.models.Weather;
import com.yehorbukh.springshell.models.WeatherType;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class WeatherRepo {
    private static final String KEY = "5a389c49c495639d5153758bca6606ac";

    private static final String apiURL = "https://api.openweathermap.org/data/2.5/" +
            "weather?q={REGION}&appid=" + KEY;

    private String insertRegionIntoApiUrl(String region) {
        return apiURL.replace("{REGION}", region);
    }

//    private Weather getWeather(String region) {
//        // get an api url with correct region name
//        String urlApiWithRegion = insertRegionIntoApiUrl(region);
//
//        return builder.baseUrl(urlApiWithRegion)
//                      .build()
//                      .get()
//                      .accept(MediaType.APPLICATION_JSON)
//                      .retrieve()
//                      .bodyToMono(Weather.class)
//                      .block();
//    }

    private String executeWeatherRequest(String region)
            throws IOException, InterruptedException, RegionNotFoundException
    {
        // get an api url with correct region name
        String urlApiWithRegion = insertRegionIntoApiUrl(region);

        var client = HttpClient.newBuilder().build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(urlApiWithRegion))
                .GET()
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400 && response.statusCode() < 500)
            throw new RegionNotFoundException("Region " + region + " wasn't found");

        return response.body();
    }

    private WeatherType defineWeatherType(int weatherId) {
        if (weatherId == 800)
            return WeatherType.CLEAR;

        int type = weatherId % 100;
        switch (type) {
            case 2 -> { return WeatherType.THUNDERSTORM; }
            case 3 -> { return WeatherType.DRIZZLE; }
            case 5 -> { return WeatherType.RAIN; }
            case 6 -> { return WeatherType.SNOW; }
            case 7 -> { return WeatherType.ATMOSPHERE; }
            case 8 -> { return WeatherType.CLOUDS; }
        }
        return null;
    }

    public Weather currentWeather(String region)
            throws IOException, InterruptedException, RegionNotFoundException
    {
        var json = new JSONObject(executeWeatherRequest(region));
        var requireJsonObject = json
                    .getJSONArray("weather")
                    .getJSONObject(0);

        var weather = new Weather();

        weather.setWeatherType(defineWeatherType(requireJsonObject.getInt("id")));
        weather.setStringType(requireJsonObject.getString("main"));
        weather.setDescription(requireJsonObject.getString("description"));

        return weather;
    }
}
