package com.mikail.weather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mikail.weather.controller.validation.CityNameConstraint;
import com.mikail.weather.dto.WeatherDto;
import com.mikail.weather.service.WeatherService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/api/weather")
@Validated
public class WeatherApi {

    private final WeatherService weatherService;

    public WeatherApi(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    @RateLimiter(name ="basic")
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city")  @CityNameConstraint @NotBlank String city){
        return  ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }


    
}
