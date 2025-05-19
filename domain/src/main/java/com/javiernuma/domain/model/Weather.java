package com.javiernuma.domain.model;

import java.util.Objects;

public record Weather(
        String city,
        Temperature temperature,
        String condition,
        Wind wind
) {
    // Records en Java 17 proporcionan automáticamente constructor, getters, equals, hashCode y toString

    // Builder pattern con records en Java 17
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String city;
        private Temperature temperature;
        private String condition;
        private Wind wind;

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder temperature(Temperature temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder wind(Wind wind) {
            this.wind = wind;
            return this;
        }

        public Weather build() {
            return new Weather(city, temperature, condition, wind);
        }
    }
}

