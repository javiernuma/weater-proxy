package com.javiernuma.domain.model;

public record Wind(
        double speed,
        String unit
) {
    // Constructor con validación
    public Wind {
        if (!unit.equals("km/h") && !unit.equals("mph")) {
            throw new IllegalArgumentException("Wind unit must be km/h or mph");
        }
    }

    public static Wind ofKmh(double speed) {
        return new Wind(speed, "km/h");
    }

    public static Wind ofMph(double speed) {
        return new Wind(speed, "mph");
    }
}