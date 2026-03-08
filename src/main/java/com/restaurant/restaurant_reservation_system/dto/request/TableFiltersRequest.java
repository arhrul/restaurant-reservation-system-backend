package com.restaurant.restaurant_reservation_system.dto.request;

import com.restaurant.restaurant_reservation_system.models.enums.Preference;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TableFiltersRequest {

    private LocalDateTime startTime;
    private int numberOfGuests;
    private List<Preference> preferences;
}
