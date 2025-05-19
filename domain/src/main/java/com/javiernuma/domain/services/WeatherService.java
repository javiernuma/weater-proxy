package com.javiernuma.domain.services;

import com.javiernuma.domain.model.RequestStatus;
import com.javiernuma.domain.model.Weather;
import com.javiernuma.domain.model.WeatherRequest;
import com.javiernuma.domain.ports.in.GetWeatherUseCase;
import com.javiernuma.domain.ports.out.WeatherProvider;
import com.javiernuma.domain.ports.out.WeatherRequestRepository;

public class WeatherService implements GetWeatherUseCase {

    private final WeatherRequestRepository weatherRequestRepository;
    private final WeatherProviderFactory weatherProviderFactory;

    public WeatherService(WeatherRequestRepository weatherRequestRepository,
                          WeatherProviderFactory weatherProviderFactory) {
        this.weatherRequestRepository = weatherRequestRepository;
        this.weatherProviderFactory = weatherProviderFactory;
    }

    @Override
    public Weather getWeatherByCity(String city, String source, String config) {
        WeatherRequest.Builder requestBuilder = WeatherRequest.builder()
                .city(city)
                .source(source)
                .config(config);

        try {
            WeatherProvider provider = weatherProviderFactory.getProvider(source);
            Weather weather = provider.getWeatherByCity(city, config);

            // Guardar petición exitosa
            weatherRequestRepository.save(requestBuilder
                    .status(RequestStatus.SUCCESS)
                    .build());

            return weather;
        } catch (Exception e) {
            // Guardar petición fallida
            weatherRequestRepository.save(requestBuilder
                    .status(RequestStatus.FAIL)
                    .error(e.getMessage())
                    .build());

            throw e;
        }
    }
}
