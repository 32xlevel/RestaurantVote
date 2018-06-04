package ru.s32xlevel.service;

import ru.s32xlevel.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {
    Vote vote(int restaurantId, int userId, LocalDateTime localTime);

    Vote get(int userId, LocalDateTime date);

    List<Vote> getAll(LocalDateTime date);

    List<Vote> getAllBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Vote> getAllToRestaurant(LocalDateTime date, int rId);

    List<Vote> getAllToRestaurantHistory(LocalDateTime startDate, LocalDateTime endDate, int rId);
}
