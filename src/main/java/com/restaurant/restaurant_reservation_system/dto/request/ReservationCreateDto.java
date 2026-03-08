package com.restaurant.restaurant_reservation_system.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationCreateDto {

    private String firstname;
    private String lastname;
    private String email;

    private Long tableId;

    private LocalDateTime startTime;

    private int numberOfGuests;

    private boolean prefWindow;
    private boolean prefPrivate;
    private boolean prefAccessible;
}