package com.mikail.weather.dto;

import com.mikail.weather.model.WeatherEntity;

public record WeatherDto(
    String cityNameString,
    String country,
    Integer temprature
) {
    public static WeatherDto convert(WeatherEntity from){
        return new WeatherDto(from.getCityName(), from.getCountry(),from.getTemprature());
    }
}
