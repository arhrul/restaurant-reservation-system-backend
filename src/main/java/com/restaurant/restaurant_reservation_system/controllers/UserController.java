package com.restaurant.restaurant_reservation_system.controllers;

import com.restaurant.restaurant_reservation_system.models.domain.User;
import com.restaurant.restaurant_reservation_system.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
