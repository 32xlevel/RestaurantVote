package ru.s32xlevel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.model.Vote;
import ru.s32xlevel.repository.VoteRepository;
import ru.s32xlevel.service.VoteService;
import ru.s32xlevel.util.DateTimeUtil;

import java.time.LocalDate;
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
    public Vote vote(int restaurantId, int userId, LocalTime localTime) {
        Vote vote = get(userId, LocalDate.now());
        if(vote == null) {
            vote = new Vote(userId, LocalDate.now());
        }

        vote = !vote.isNew() && localTime.isAfter(LocalTime.of(11, 0)) ? null : repository.save(vote, restaurantId);
        return checkNotFoundWithId(vote, restaurantId);
    }

    @Override
    public Vote get(int userId, LocalDate date) {
        date = DateTimeUtil.nullToNow(date);
        return repository.get(date, userId);
    }

    @Override
    public List<Vote> getAll(LocalDate date) {
        date = DateTimeUtil.nullToNow(date);
        return repository.getAll(date);
    }

    @Override
    public List<Vote> getAllBetween(LocalDate startDate, LocalDate endDate) {
        startDate = DateTimeUtil.nullToMin(startDate);
        endDate = DateTimeUtil.nullToMax(endDate);
        return repository.getBetween(startDate, endDate);
    }

    @Override
    public List<Vote> getAllToRestaurant(LocalDate date, int rId) {
        date = DateTimeUtil.nullToNow(date);
        return repository.getAllToRestaurant(date, rId);
    }

    @Override
    public List<Vote> getAllToRestaurantHistory(LocalDate startDate, LocalDate endDate, int rId) {
        startDate = DateTimeUtil.nullToMin(startDate);
        endDate = DateTimeUtil.nullToMax(endDate);
        return repository.getAllToRestaurantHistory(startDate, endDate, rId);
    }
}
