package com.restaurant.restaurant_reservation_system.repositories;

import com.restaurant.restaurant_reservation_system.models.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
