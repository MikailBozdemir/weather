package com.mikail.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Current(
    Integer temperature,
    String observation_time
) {
    
}
