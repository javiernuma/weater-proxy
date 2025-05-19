package com.javiernuma.domain.ports.in;

import com.javiernuma.domain.model.Weather;

public interface GetWeatherUseCase {
    Weather getWeatherByCity(String city, String source, String config);
}
