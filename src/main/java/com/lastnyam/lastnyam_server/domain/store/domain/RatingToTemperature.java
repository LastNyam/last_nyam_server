package com.lastnyam.lastnyam_server.domain.store.domain;

public enum RatingToTemperature {
    ZERO(0.0),
    ONE(-1.0),
    TWO(-0.5),
    THREE(0.0),
    FOUR(0.5),
    FIVE(1.0);

    private final double temperature;

    RatingToTemperature(double temperature) {
        this.temperature = temperature;
    }

    public static double getTemperature(int rating) {
        return values()[rating].temperature;
    }
}
