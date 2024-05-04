package com.mikail.weather.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikail.weather.constants.Constants;
import com.mikail.weather.dto.WeatherDto;
import com.mikail.weather.dto.WeatherResponse;
import com.mikail.weather.model.WeatherEntity;
import com.mikail.weather.repository.WeatherRepository;

import jakarta.annotation.PostConstruct;

@Service
@CacheConfig(cacheNames = {"weathers"})
public class WeatherService {

private static final Logger logger =LoggerFactory.getLogger(WeatherService.class);

private final WeatherRepository weatherRepository;

private final RestTemplate restTemplate;

private final ObjectMapper objectMapper =new ObjectMapper();





    private static final  String API_URL="http://api.weatherstack.com/current?access_key=5270da4a36b9eed41ad4f0cb182048d0&query=";

    public WeatherService(WeatherRepository weatherRepository,RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate=restTemplate;
    }
    
    @Cacheable(key="#city")
    public WeatherDto getWeatherByCityName(String city){
        logger.info("requested city"+city);
        Optional<WeatherEntity> weathherEntityOptional =weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(city);
      
       return weathherEntityOptional.map(weather ->{
        if(weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))){
            return  WeatherDto.convert(getWeatherFromWeatherStack(city));
        }
        return WeatherDto.convert(weathherEntityOptional.get());
       })
       .orElseGet(()->WeatherDto.convert(getWeatherFromWeatherStack(city)));

    }

   
    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity= restTemplate.getForEntity(getWeatherStrackUrl(city), String.class);

        try {
            logger.info(responseEntity.getBody());
            WeatherResponse weatherResponse= objectMapper.readValue(responseEntity.getBody(),WeatherResponse.class);
            
            return saveWeatherEntity(city, weatherResponse);
        } catch (JsonProcessingException e) {
            logger.error("Weather API'den gelen JSON yanıtını işlerken hata oluştu", e);
            throw new RuntimeException("Weather API'den gelen JSON yanıtını işlerken hata oluştu", e);
        }
        
    }

    @CacheEvict(allEntries = true)
    @Scheduled(fixedRateString = "10000")
    @PostConstruct
    public void clearCache(){
        logger.info("cache cleared");
    }

    public WeatherEntity saveWeatherEntity(String city,WeatherResponse weatherResponse){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity =new WeatherEntity(
            city,
            weatherResponse.location().name()
            ,weatherResponse.location().country()
            ,weatherResponse.current().temperature()
            ,LocalDateTime.now(),LocalDateTime.parse(weatherResponse.location().localtime(),dateTimeFormatter)
        );

        return weatherRepository.save(weatherEntity);
    }

    private String getWeatherStrackUrl(String city){
        logger.info(Constants.API_URL+Constants.ACCESS_KEY_PARAM+Constants.API_KEY+Constants.QUERY_KEY_PARAM+city);
        return  Constants.API_URL+Constants.ACCESS_KEY_PARAM+Constants.API_KEY+Constants.QUERY_KEY_PARAM+city;
    }
}
