package com.restaurant.restaurant_reservation_system.services;

import com.restaurant.restaurant_reservation_system.dto.request.TableFiltersRequest;
import com.restaurant.restaurant_reservation_system.dto.response.TableResponse;
import com.restaurant.restaurant_reservation_system.models.domain.Reservation;
import com.restaurant.restaurant_reservation_system.models.domain.RestaurantTable;
import com.restaurant.restaurant_reservation_system.models.enums.Preference;
import com.restaurant.restaurant_reservation_system.repositories.ReservationRepository;
import com.restaurant.restaurant_reservation_system.repositories.RestaurantTableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;
    private final ReservationRepository reservationRepository;

    public RestaurantTableService(
            RestaurantTableRepository restaurantTableRepository,
            ReservationRepository reservationRepository
    ) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<TableResponse> getFilteredTables(TableFiltersRequest filters) {
        List<RestaurantTable> tables = this.restaurantTableRepository.findAll();


        return tables.stream()
                .map(t -> {
                    TableResponse tr = new TableResponse();
                    tr.setId(t.getId());
                    tr.setX(t.getX());
                    tr.setY(t.getY());
                    tr.setCapacity(t.getCapacity());
                    tr.setWindow(t.isWindow());
                    tr.setPrivate(t.isPrivate());
                    tr.setAccessible(t.isAccessible());
                    tr.setAdvised(this.checkAdvised(t, filters));
                    return tr;
                })
                .toList();
    }

    private boolean checkAdvised(RestaurantTable table, TableFiltersRequest filters) {
        int guests = filters.getNumberOfGuests();

        if (table.getCapacity() < guests) {
            return false;
        }

        if (!checkAvailability(table, filters)) {
            return false;
        }

        if (!matchesPreferences(table, filters.getPreferences())) {
            return false;
        }

        List<RestaurantTable> tables = restaurantTableRepository.findAll();

        int bestCapacity = tables.stream()
                .filter(t -> t.getCapacity() >= guests)
                .filter(t -> matchesPreferences(t, filters.getPreferences()))
                .mapToInt(RestaurantTable::getCapacity)
                .min()
                .orElse(Integer.MAX_VALUE);

        return table.getCapacity() == bestCapacity;
    }

    private boolean matchesPreferences(RestaurantTable table, List<Preference> preferences) {

        if (preferences == null || preferences.isEmpty()) {
            return true;
        }

        for (Preference pref : preferences) {
            switch (pref) {
                case WINDOW -> {
                    if (!table.isWindow()) return false;
                }
                case PRIVATE -> {
                    if (!table.isPrivate()) return false;
                }
                case ACCESSIBLE -> {
                    if (!table.isAccessible()) return false;
                }
            }
        }

        return true;
    }

    private boolean checkAvailability(RestaurantTable table, TableFiltersRequest filters) {

        List<Reservation> reservations = reservationRepository.findAllByTable(table);

        LocalDateTime desiredStart = filters.getStartTime();
        LocalDateTime desiredEnd = desiredStart.plusHours(2);

        for (Reservation res : reservations) {
            if (!(res.getEndTime().isBefore(desiredStart) || res.getStartTime().isAfter(desiredEnd))) {
                return false;
            }
        }

        return true;
    }
}
