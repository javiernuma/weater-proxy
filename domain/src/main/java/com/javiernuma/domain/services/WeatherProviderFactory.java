package com.javiernuma.domain.services;

import com.javiernuma.domain.ports.out.WeatherProvider;

public interface WeatherProviderFactory {

    WeatherProvider getProvider(String source);
}
