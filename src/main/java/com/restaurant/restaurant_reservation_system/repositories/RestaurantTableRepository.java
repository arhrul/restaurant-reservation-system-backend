package com.restaurant.restaurant_reservation_system.repositories;

import com.restaurant.restaurant_reservation_system.models.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}
