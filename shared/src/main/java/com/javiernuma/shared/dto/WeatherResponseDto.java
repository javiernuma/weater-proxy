package com.javiernuma.shared.dto;

public record WeatherResponseDto(
        String city,
        TemperatureDto temperature,
        String condition,
        WindDto wind
) {}
