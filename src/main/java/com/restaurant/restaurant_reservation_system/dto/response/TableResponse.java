package com.restaurant.restaurant_reservation_system.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TableResponse {

    @NotNull
    private Long id;

    @NotNull
    private int x;

    @NotNull
    private int y;

    @NotNull
    private int capacity;

    @NotNull
    private boolean isWindow;

    @NotNull
    private boolean isPrivate;

    @NotNull
    private boolean isAccessible;

    @NotNull
    private boolean isAdvised;
}
