package com.javiernuma.adapters.out.persistence.repository;


import com.javiernuma.adapters.out.persistence.entity.WeatherRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaWeatherRequestRepository extends JpaRepository<WeatherRequestEntity, UUID> {
}