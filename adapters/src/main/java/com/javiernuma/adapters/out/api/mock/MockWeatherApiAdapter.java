package com.javiernuma.adapters.out.api.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.javiernuma.domain.model.Temperature;
import com.javiernuma.domain.model.Weather;
import com.javiernuma.domain.model.Wind;
import com.javiernuma.domain.ports.out.WeatherProvider;
import com.javiernuma.shared.exception.WeatherApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MockWeatherApiAdapter implements WeatherProvider {

    private static final Logger logger = LoggerFactory.getLogger(MockWeatherApiAdapter.class);
    private static final Random random = new Random();
    private static final Map<String, Weather> cache = new ConcurrentHashMap<>();
    private static final String[] CONDITIONS = {
            "Sunny", "Cloudy", "Rainy", "Snowy", "Windy", "Foggy", "Partly Cloudy"
    };

    private final ObjectMapper objectMapper;

    public MockWeatherApiAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Weather getWeatherByCity(String city, String configStr) {
        logger.info("Getting mock weather for city: {}", city);

        try {
            // Parse config if provided
            Map<String, String> config = parseConfig(configStr);

            // Check if we should simulate failure
            if (Boolean.parseBoolean(config.getOrDefault("simulateFailure", "false"))) {
                throw new WeatherApiException("Simulated failure for mock API", 500);
            }

            // Check cache
            if (cache.containsKey(city)) {
                logger.info("Returning cached weather for city: {}", city);
                return cache.get(city);
            }

            // Generate random weather
            Weather weather = generateRandomWeather(city);

            // Cache result
            cache.put(city, weather);

            return weather;

        } catch (JsonProcessingException e) {
            logger.error("Error parsing config: {}", e.getMessage());
            throw new WeatherApiException("Invalid configuration format", 400, e);
        } catch (WeatherApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error getting mock weather: {}", e.getMessage());
            throw new WeatherApiException("Error getting mock weather", 500, e);
        }
    }

    private Map<String, String> parseConfig(String configStr) throws JsonProcessingException {
        if (configStr == null || configStr.isEmpty() || configStr.equals("{}")) {
            return Map.of();
        }
        return objectMapper.readValue(configStr, Map.class);
    }

    private Weather generateRandomWeather(String city) {
        // Generate random temperature between -10 and 40 degrees Celsius
        double temperature = -10 + random.nextDouble() * 50;

        // Generate random wind speed between 0 and 100 km/h
        double windSpeed = random.nextDouble() * 100;

        // Select random condition
        String condition = CONDITIONS[random.nextInt(CONDITIONS.length)];

        return Weather.builder()
                .city(city)
                .temperature(Temperature.ofCelsius(Math.round(temperature * 10.0) / 10.0))
                .condition(condition)
                .wind(Wind.ofKmh(Math.round(windSpeed * 10.0) / 10.0))
                .build();
    }
}
