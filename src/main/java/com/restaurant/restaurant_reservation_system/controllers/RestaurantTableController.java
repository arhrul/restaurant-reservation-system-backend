package com.restaurant.restaurant_reservation_system.controllers;

import com.restaurant.restaurant_reservation_system.dto.request.TableFiltersRequest;
import com.restaurant.restaurant_reservation_system.dto.response.TableResponse;
import com.restaurant.restaurant_reservation_system.models.domain.RestaurantTable;
import com.restaurant.restaurant_reservation_system.models.enums.Preference;
import com.restaurant.restaurant_reservation_system.repositories.RestaurantTableRepository;
import com.restaurant.restaurant_reservation_system.services.RestaurantTableService;
import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tables")
@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantTableController {

    private final RestaurantTableRepository restaurantTableRepository;
    private final RestaurantTableService restaurantTableService;

    public RestaurantTableController(
            RestaurantTableRepository restaurantTableRepository,
            RestaurantTableService restaurantTableService
    ) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.restaurantTableService = restaurantTableService;
    }

    @GetMapping
    public List<RestaurantTable> getTables() {
        return this.restaurantTableRepository.findAll();
    }

    @GetMapping("/filtered")
    public List<TableResponse> getFilteredTables(
            @RequestParam LocalDateTime startTime,
            @RequestParam int numberOfGuests,
            @RequestParam @Nullable List<Integer> preferences
    ) {

        if (preferences == null) preferences = new ArrayList<>();

        TableFiltersRequest filters = new TableFiltersRequest();
        filters.setStartTime(startTime);
        filters.setNumberOfGuests(numberOfGuests);
        filters.setPreferences(preferences.stream().map(Preference::fromInteger).toList());

        return this.restaurantTableService.getFilteredTables(filters);
    }
}
