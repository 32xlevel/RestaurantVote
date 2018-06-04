package ru.s32xlevel.repository;

import ru.s32xlevel.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository {
    List<Vote> getBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Vote> getAll(LocalDateTime date);

    List<Vote> getAllToRestaurant(LocalDateTime date, int restaurantId);

    List<Vote> getAllToRestaurantHistory(LocalDateTime startDate, LocalDateTime endDate, int restaurantId);

    Vote save(Vote vote, int restaurantId);

    Vote get(LocalDateTime dateTime, int userId);
}
