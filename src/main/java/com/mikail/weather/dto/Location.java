package com.mikail.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Location(
    String name,
    String country,
    String localtime,
    String region,
    String lat,
    String lon,
     String timezone_id,
    String localtime_epoch	,
    String utc_offset	

) {
    
}
