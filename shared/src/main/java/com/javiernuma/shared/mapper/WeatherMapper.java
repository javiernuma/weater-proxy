package com.javiernuma.shared.mapper;

import com.javiernuma.domain.model.Temperature;
import com.javiernuma.domain.model.Weather;
import com.javiernuma.domain.model.Wind;
import com.javiernuma.shared.dto.TemperatureDto;
import com.javiernuma.shared.dto.WeatherResponseDto;
import com.javiernuma.shared.dto.WindDto;

public class WeatherMapper {

    private WeatherMapper() {}

    public static WeatherResponseDto toDto(Weather weather) {
        return new WeatherResponseDto(
                weather.city(),
                new TemperatureDto(weather.temperature().value(), weather.temperature().unit()),
                weather.condition(),
                new WindDto(weather.wind().speed(), weather.wind().unit())
        );
    }

    public static Weather toDomain(WeatherResponseDto dto) {
        return Weather.builder()
                .city(dto.city())
                .temperature(new Temperature(dto.temperature().value(), dto.temperature().unit()))
                .condition(dto.condition())
                .wind(new Wind(dto.wind().speed(), dto.wind().unit()))
                .build();
    }
}
