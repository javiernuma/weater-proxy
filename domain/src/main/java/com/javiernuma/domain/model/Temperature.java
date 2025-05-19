package com.javiernuma.domain.model;

public record Temperature(
        double value,
        String unit
) {
    // Constructor con validación
    public Temperature {
        if (!unit.equals("°C") && !unit.equals("°F")) {
            throw new IllegalArgumentException("Temperature unit must be °C or °F");
        }
    }

    public static Temperature ofCelsius(double value) {
        return new Temperature(value, "°C");
    }

    public static Temperature ofFahrenheit(double value) {
        return new Temperature(value, "°F");
    }
}