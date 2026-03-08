package com.restaurant.restaurant_reservation_system.services;

import com.restaurant.restaurant_reservation_system.dto.request.ReservationCreateDto;
import com.restaurant.restaurant_reservation_system.models.domain.Reservation;
import com.restaurant.restaurant_reservation_system.models.domain.RestaurantTable;
import com.restaurant.restaurant_reservation_system.models.domain.User;
import com.restaurant.restaurant_reservation_system.repositories.ReservationRepository;
import com.restaurant.restaurant_reservation_system.repositories.RestaurantTableRepository;
import com.restaurant.restaurant_reservation_system.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository tableRepository;
    private final UserRepository userRepository;

    public void createReservation(ReservationCreateDto dto) {

        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setFirstname(dto.getFirstname());
                    newUser.setLastname(dto.getLastname());
                    newUser.setEmail(dto.getEmail());
                    return userRepository.save(newUser);
                });

        RestaurantTable table = tableRepository.findById(dto.getTableId())
                .orElseThrow();

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setTable(table);
        reservation.setStartTime(dto.getStartTime());
        reservation.setEndTime(dto.getStartTime().plusHours(2));
        reservation.setGuestCount(dto.getNumberOfGuests());

        reservation.setPrefWindow(dto.isPrefWindow());
        reservation.setPrefPrivate(dto.isPrefPrivate());
        reservation.setPrefAccessible(dto.isPrefAccessible());

        reservationRepository.save(reservation);
    }
}