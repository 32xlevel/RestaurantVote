package ru.s32xlevel.repository;

import ru.s32xlevel.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository {
    List<Vote> getBetween(LocalDate startDate, LocalDate endDate);

    List<Vote> getAll(LocalDate date);

    List<Vote> getAllToRestaurant(LocalDate date, int restaurantId);

    List<Vote> getAllToRestaurantHistory(LocalDate startDate, LocalDate endDate, int restaurantId);

    Vote save(Vote vote, int restaurantId);

    Vote get(LocalDate dateTime, int userId);
}
