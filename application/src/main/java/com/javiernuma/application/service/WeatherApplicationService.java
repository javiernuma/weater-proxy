package com.javiernuma.application.service;

import com.javiernuma.domain.model.Weather;
import com.javiernuma.domain.ports.in.GetWeatherUseCase;
import com.javiernuma.shared.dto.WeatherResponseDto;
import com.javiernuma.shared.mapper.WeatherMapper;

public class WeatherApplicationService {

    private final GetWeatherUseCase getWeatherUseCase;

    public WeatherApplicationService(GetWeatherUseCase getWeatherUseCase) {
        this.getWeatherUseCase = getWeatherUseCase;
    }

    public WeatherResponseDto getWeatherByCity(String city, String source, String config) {
        Weather weather = getWeatherUseCase.getWeatherByCity(city, source, config);
        return WeatherMapper.toDto(weather);
    }
}
