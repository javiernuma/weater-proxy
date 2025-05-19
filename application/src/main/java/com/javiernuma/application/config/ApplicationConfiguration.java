package com.javiernuma.application.config;

import com.javiernuma.application.service.WeatherApplicationService;
import com.javiernuma.domain.ports.in.GetWeatherUseCase;
import com.javiernuma.domain.ports.out.WeatherRequestRepository;
import com.javiernuma.domain.services.WeatherProviderFactory;
import com.javiernuma.domain.services.WeatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public GetWeatherUseCase getWeatherUseCase(
            WeatherRequestRepository weatherRequestRepository,
            WeatherProviderFactory weatherProviderFactory) {
        return new WeatherService(weatherRequestRepository, weatherProviderFactory);
    }

    @Bean
    public WeatherApplicationService weatherApplicationService(GetWeatherUseCase getWeatherUseCase) {
        return new WeatherApplicationService(getWeatherUseCase);
    }
}
