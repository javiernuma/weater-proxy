package com.javiernuma.shared.exception;

public class WeatherProviderNotFoundException extends RuntimeException {
    public WeatherProviderNotFoundException(String source) {
        super("Weather provider not found for source: " + source);
    }
}
