package ru.s32xlevel.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.s32xlevel.model.Vote;
import ru.s32xlevel.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository voteRepository;

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Override
    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate) {
        return voteRepository.findAllByDateTimeBetween(startDate, endDate);
    }

    @Override
    public List<Vote> getAll(LocalDate date) {
        return voteRepository.findAllByDateTime(date);
    }

    @Override
    public List<Vote> getAllToRestaurant(LocalDate date, int restaurantId) {
        return voteRepository.findAllByDateTimeAndRestaurantId(date, restaurantId);
    }

    @Override
    public List<Vote> getAllToRestaurantHistory(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return voteRepository.findAllToRestaurantHistory(restaurantId, startDate, endDate);
    }

    @Override
    public Vote save(Vote vote, int restaurantId) {
        if(!vote.isNew() && get(vote.getId(), restaurantId) == null) {
            return null;
        }
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        return voteRepository.save(vote);
    }

    @Override
    public Vote get(LocalDate date, int userId) {
        return voteRepository.get(date, userId);
    }

    public Vote get(int id, int restaurantId) {
        return voteRepository.findByIdAndRestaurantId(id, restaurantId).orElse(null);
    }
}
