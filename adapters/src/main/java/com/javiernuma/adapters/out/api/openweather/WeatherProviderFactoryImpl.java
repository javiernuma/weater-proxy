package com.javiernuma.adapters.out.api.openweather;

import com.javiernuma.domain.ports.out.WeatherProvider;
import com.javiernuma.domain.services.WeatherProviderFactory;
import com.javiernuma.adapters.out.api.mock.MockWeatherApiAdapter;
import com.javiernuma.shared.exception.WeatherProviderNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeatherProviderFactoryImpl implements WeatherProviderFactory {

    private final Map<String, WeatherProvider> providers = new ConcurrentHashMap<>();

    public WeatherProviderFactoryImpl(
            MockWeatherApiAdapter mockWeatherApiAdapter,
            OpenWeatherApiAdapter openWeatherApiAdapter) {
        providers.put("mock", mockWeatherApiAdapter);
        providers.put("openweather", openWeatherApiAdapter);
    }

    @Override
    public WeatherProvider getProvider(String source) {
        WeatherProvider provider = providers.get(source.toLowerCase());
        if (provider == null) {
            throw new WeatherProviderNotFoundException(source);
        }
        return provider;
    }
}
