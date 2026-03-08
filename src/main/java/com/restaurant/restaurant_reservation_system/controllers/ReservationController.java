package com.restaurant.restaurant_reservation_system.controllers;

import com.restaurant.restaurant_reservation_system.dto.request.ReservationCreateDto;
import com.restaurant.restaurant_reservation_system.models.domain.Reservation;
import com.restaurant.restaurant_reservation_system.repositories.ReservationRepository;
import com.restaurant.restaurant_reservation_system.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ReservationController(ReservationRepository reservationRepository, ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateDto dto) {

        reservationService.createReservation(dto);

        return ResponseEntity.ok().build();
    }
}
