package com.yehorbukh.springshell.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private WeatherType weatherType;
    private String stringType;
    private String description;
}
