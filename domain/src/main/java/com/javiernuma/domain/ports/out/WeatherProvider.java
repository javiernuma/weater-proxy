package com.javiernuma.domain.ports.out;

import com.javiernuma.domain.model.Weather;

public interface WeatherProvider {
    Weather getWeatherByCity(String city, String config);
}
