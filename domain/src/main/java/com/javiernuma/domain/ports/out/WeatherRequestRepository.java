package com.javiernuma.domain.ports.out;


import com.javiernuma.domain.model.WeatherRequest;

public interface WeatherRequestRepository {
    void save(WeatherRequest weatherRequest);
}
