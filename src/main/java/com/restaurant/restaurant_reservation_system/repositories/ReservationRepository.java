package com.restaurant.restaurant_reservation_system.repositories;

import com.restaurant.restaurant_reservation_system.models.domain.Reservation;
import com.restaurant.restaurant_reservation_system.models.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByTable(RestaurantTable table);
}
