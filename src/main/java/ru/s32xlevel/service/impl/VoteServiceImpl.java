package ru.s32xlevel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.model.Vote;
import ru.s32xlevel.repository.VoteRepository;
import ru.s32xlevel.service.VoteService;
import ru.s32xlevel.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.s32xlevel.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository repository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Vote vote(int restaurantId, int userId, LocalDateTime dateTime) {
        Vote vote = get(userId, dateTime);
        if(vote == null) {
            vote = new Vote(userId, LocalDateTime.now());
        }

        vote = !vote.isNew() && dateTime.toLocalTime().isAfter(LocalTime.of(11, 0)) ? null : repository.save(vote, restaurantId);
        return checkNotFoundWithId(vote, restaurantId);
    }

    @Override
    public Vote get(int userId, LocalDateTime date) {
        date = DateTimeUtil.nullToNow(date);
        return repository.get(date, userId);
    }

    @Override
    public List<Vote> getAll(LocalDateTime date) {
        return null;
    }

    @Override
    public List<Vote> getAllBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public List<Vote> getAllToRestaurant(LocalDateTime date, int rId) {
        return null;
    }

    @Override
    public List<Vote> getAllToRestaurantHistory(LocalDateTime startDate, LocalDateTime endDate, int rId) {
        return null;
    }
}
