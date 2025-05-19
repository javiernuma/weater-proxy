package com.javiernuma.adapters.out.api.openweather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javiernuma.domain.model.Temperature;
import com.javiernuma.domain.model.Weather;
import com.javiernuma.domain.model.Wind;
import com.javiernuma.domain.ports.out.WeatherProvider;
import com.javiernuma.shared.exception.WeatherApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OpenWeatherApiAdapter implements WeatherProvider {

    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherApiAdapter.class);
    private static final String DEFAULT_BASE_URL = "https://api.openweathermap.org/data/2.5";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenWeatherApiAdapter(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Weather getWeatherByCity(String city, String configStr) {
        logger.info("Getting OpenWeather data for city: {}", city);

        try {
            // Parse config
            Map<String, String> config = parseConfig(configStr);

            // Get API key and base URL from config
            String apiKey = config.get("apiKey");
            if (apiKey == null || apiKey.isBlank()) {
                throw new WeatherApiException("API key is required for OpenWeather API", 400);
            }

            String baseUrl = config.getOrDefault("baseUrl", DEFAULT_BASE_URL);

            // Build URL
            String url = String.format("%s/weather?q=%s&appid=%s&units=metric",
                    baseUrl, city, apiKey);

            // Make request
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Parse response
            return parseResponse(response.getBody(), city);

        } catch (JsonProcessingException e) {
            logger.error("Error parsing config or response: {}", e.getMessage());
            throw new WeatherApiException("Invalid configuration or response format", 400, e);
        } catch (RestClientException e) {
            logger.error("Error calling OpenWeather API: {}", e.getMessage());
            throw new WeatherApiException("Error calling OpenWeather API", 502, e);
        } catch (WeatherApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error getting weather from OpenWeather: {}", e.getMessage());
            throw new WeatherApiException("Error getting weather", 500, e);
        }
    }

    private Map<String, String> parseConfig(String configStr) throws JsonProcessingException {
        if (configStr == null || configStr.isEmpty() || configStr.equals("{}")) {
            return Map.of();
        }
        return objectMapper.readValue(configStr, Map.class);
    }

    private Weather parseResponse(String responseBody, String city) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(responseBody);

        double temp = root.path("main").path("temp").asDouble();
        double windSpeed = root.path("wind").path("speed").asDouble();
        String description = root.path("weather").path(0).path("main").asText();

        return Weather.builder()
                .city(city)
                .temperature(Temperature.ofCelsius(temp))
                .condition(description)
                .wind(Wind.ofKmh(windSpeed))
                .build();
    }
}
