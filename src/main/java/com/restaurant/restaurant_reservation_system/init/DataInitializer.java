package com.restaurant.restaurant_reservation_system.init;

import com.restaurant.restaurant_reservation_system.models.domain.Reservation;
import com.restaurant.restaurant_reservation_system.models.domain.RestaurantTable;
import com.restaurant.restaurant_reservation_system.models.domain.User;
import com.restaurant.restaurant_reservation_system.repositories.ReservationRepository;
import com.restaurant.restaurant_reservation_system.repositories.RestaurantTableRepository;
import com.restaurant.restaurant_reservation_system.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RestaurantTableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    private final Random random = new Random();

    public DataInitializer(UserRepository userRepository,
                           RestaurantTableRepository tableRepository,
                           ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) {

        for (int i = 1; i <= 8; i++) {
            User user = new User();
            user.setFirstname("User");
            user.setLastname(String.valueOf(i));
            user.setEmail("user" + i + "@example.com");
            userRepository.save(user);
        }

        List<RestaurantTable> createdTables = new ArrayList<>();

        int[] rows = {1, 3, 5, 7};

        int[][] capacities = {
                {2,2,2,2,4,4,6},
                {2,2,2,4,4,6,8},
                {2,2,4,4,6,8,10},
                {2,2,2,4,6,8,10}
        };

        int lastColumn = 7;
        int firstRow = rows[0];
        int lastRow = rows[rows.length - 1];

        for (int r = 0; r < rows.length; r++) {

            int y = rows[r];
            int x = 1;

            for (int c = 0; c < capacities[r].length; c++) {

                RestaurantTable table = new RestaurantTable();

                table.setCapacity(capacities[r][c]);
                table.setX(x);
                table.setY(y);

                table.setWindow(x == 1);

                boolean isCorner =
                        (x == 1 && y == firstRow) ||
                                (x == lastColumn && y == firstRow) ||
                                (x == 1 && y == lastRow) ||
                                (x == lastColumn && y == lastRow);

                table.setPrivate(isCorner);

                table.setAccessible(random.nextDouble() < 0.15);

                tableRepository.save(table);
                createdTables.add(table);

                x++;
            }
        }

        List<User> users = userRepository.findAll();

        for (int i = 0; i < 15; i++) {

            Reservation r = new Reservation();

            RestaurantTable table = createdTables.get(random.nextInt(createdTables.size()));

            r.setUser(users.get(random.nextInt(users.size())));
            r.setTable(table);

            int guestCount = Math.min(table.getCapacity(), random.nextInt(4) + 1);
            r.setGuestCount(guestCount);

            LocalDateTime start = LocalDateTime.now()
                    .plusDays(random.nextInt(5))
                    .withHour(17 + random.nextInt(5))
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);

            r.setStartTime(start);
            r.setEndTime(start.plusHours(2));

            r.setPrefWindow(random.nextBoolean());
            r.setPrefPrivate(random.nextDouble() < 0.3);
            r.setPrefAccessible(random.nextDouble() < 0.2);

            reservationRepository.save(r);
        }

        System.out.println("Data initialization complete.");
    }
}