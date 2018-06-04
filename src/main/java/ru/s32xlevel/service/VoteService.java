package ru.s32xlevel.service;

import ru.s32xlevel.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface VoteService {
    Vote vote(int restaurantId, int userId, LocalTime localTime);

    Vote get(int userId, LocalDate date);

    List<Vote> getAll(LocalDate date);

    List<Vote> getAllBetween(LocalDate startDate, LocalDate endDate);

    List<Vote> getAllToRestaurant(LocalDate date, int rId);

    List<Vote> getAllToRestaurantHistory(LocalDate startDate, LocalDate endDate, int rId);
}
