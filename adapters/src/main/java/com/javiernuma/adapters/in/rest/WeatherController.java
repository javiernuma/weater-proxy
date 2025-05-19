package com.javiernuma.adapters.in.rest;

import com.javiernuma.application.service.WeatherApplicationService;
import com.javiernuma.shared.dto.WeatherResponseDto;
import com.javiernuma.shared.exception.WeatherApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherApplicationService weatherApplicationService;

    public WeatherController(WeatherApplicationService weatherApplicationService) {
        this.weatherApplicationService = weatherApplicationService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponseDto> getWeatherByCity(
            @PathVariable String city,
            @RequestParam(required = false, defaultValue = "mock") String source,
            @RequestParam(required = false, defaultValue = "{}") String config) {

        logger.info("Weather request for city: {}, source: {}", city, source);

        try {
            WeatherResponseDto weather = weatherApplicationService.getWeatherByCity(city, source, config);
            return ResponseEntity.ok(weather);
        } catch (WeatherApiException e) {
            logger.error("Error getting weather for city: {}, source: {}, error: {}",
                    city, source, e.getMessage());
            return ResponseEntity.status(e.getStatusCode())
                    .body(null);
        } catch (Exception e) {
            logger.error("Unexpected error getting weather for city: {}, source: {}, error: {}",
                    city, source, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<ErrorResponseDto> handleWeatherApiException(WeatherApiException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponseDto(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto("An unexpected error occurred: " + e.getMessage()));
    }

    record ErrorResponseDto(String message) {}
}