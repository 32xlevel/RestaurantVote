package ru.s32xlevel.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.s32xlevel.model.Vote;
import ru.s32xlevel.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository voteRepository;

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Override
    public List<Vote> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return voteRepository.findAllByDateTimeBetween(startDate, endDate);
    }

    @Override
    public List<Vote> getAll(LocalDateTime dateTime) {
        return voteRepository.findAllByDateTime(dateTime);
    }

    @Override
    public List<Vote> getAllToRestaurant(LocalDateTime dateTime, int restaurantId) {
        return voteRepository.findAllByDateTimeAndRestaurantId(dateTime, restaurantId);
    }

    @Override
    public List<Vote> getAllToRestaurantHistory(LocalDateTime startDate, LocalDateTime endDate, int restaurantId) {
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
    public Vote get(LocalDateTime dateTime, int userId) {
        return voteRepository.get(dateTime, userId);
    }

    public Vote get(int id, int restaurantId) {
        return voteRepository.findByIdAndRestaurantId(id, restaurantId).orElse(null);
    }
}
