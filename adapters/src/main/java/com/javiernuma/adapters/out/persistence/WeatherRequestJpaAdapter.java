package com.javiernuma.adapters.out.persistence;

import com.javiernuma.domain.model.WeatherRequest;
import com.javiernuma.domain.ports.out.WeatherRequestRepository;
import com.javiernuma.adapters.out.persistence.entity.WeatherRequestEntity;
import com.javiernuma.adapters.out.persistence.repository.JpaWeatherRequestRepository;
import org.springframework.stereotype.Component;

@Component
public class WeatherRequestJpaAdapter implements WeatherRequestRepository {

    private final JpaWeatherRequestRepository jpaWeatherRequestRepository;

    public WeatherRequestJpaAdapter(JpaWeatherRequestRepository jpaWeatherRequestRepository) {
        this.jpaWeatherRequestRepository = jpaWeatherRequestRepository;
    }

    @Override
    public void save(WeatherRequest weatherRequest) {
        WeatherRequestEntity entity = mapToEntity(weatherRequest);
        jpaWeatherRequestRepository.save(entity);
    }

    private WeatherRequestEntity mapToEntity(WeatherRequest weatherRequest) {
        WeatherRequestEntity entity = new WeatherRequestEntity();
        entity.setId(weatherRequest.id());
        entity.setCity(weatherRequest.city());
        entity.setSource(weatherRequest.source());
        entity.setConfig(weatherRequest.config());
        entity.setTimestamp(weatherRequest.timestamp());
        entity.setStatus(weatherRequest.status());
        entity.setError(weatherRequest.error());
        return entity;
    }
}