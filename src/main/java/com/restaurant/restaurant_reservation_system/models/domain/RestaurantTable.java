package com.restaurant.restaurant_reservation_system.models.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int capacity;

    private int x;
    private int y;

    @Column(name = "is_window")
    private boolean isWindow;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "is_accessible")
    private boolean isAccessible;
}
