package com.javiernuma.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record WeatherRequest(
        UUID id,
        String city,
        String source,
        String config,
        LocalDateTime timestamp,
        RequestStatus status,
        String error
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String city;
        private String source;
        private String config;
        private LocalDateTime timestamp = LocalDateTime.now();
        private RequestStatus status;
        private String error;

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder config(String config) {
            this.config = config;
            return this;
        }

        public Builder status(RequestStatus status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public WeatherRequest build() {
            return new WeatherRequest(id, city, source, config, timestamp, status, error);
        }
    }
}

