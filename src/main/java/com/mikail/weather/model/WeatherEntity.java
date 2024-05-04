package com.mikail.weather.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WeatherEntity {

    @Id
    @GeneratedValue(generator ="UUID")
    @GenericGenerator(name ="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String requestedCityName;
    private String cityName;
    private String country;
    private Integer temprature;
    private LocalDateTime updatedTime;
    private LocalDateTime responsLocalDateTime;
     public WeatherEntity(String requestedCityName, String cityName, String country, Integer temprature,
            LocalDateTime updatedTime, LocalDateTime responsLocalDateTime) {
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temprature = temprature;
        this.updatedTime = updatedTime;
        this.responsLocalDateTime = responsLocalDateTime;
    }

    
}
