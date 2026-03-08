package com.restaurant.restaurant_reservation_system.models.enums;

public enum Preference {
    WINDOW, PRIVATE, ACCESSIBLE;

    public static Preference fromInteger(int x) {
        return switch (x) {
            case 0 -> WINDOW;
            case 1 -> PRIVATE;
            case 2 -> ACCESSIBLE;
            default -> null;
        };
    }
}
