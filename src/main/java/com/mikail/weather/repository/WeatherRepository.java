package com.mikail.weather.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikail.weather.model.WeatherEntity;

public interface WeatherRepository extends JpaRepository<WeatherEntity,String> {

    Optional<WeatherEntity> findFirstByRequestedCityNameOrderByUpdatedTimeDesc(String city);

}
