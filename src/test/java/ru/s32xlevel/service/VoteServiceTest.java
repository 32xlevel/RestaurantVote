package ru.s32xlevel.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.s32xlevel.model.Vote;
import ru.s32xlevel.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import static ru.s32xlevel.data.RestaurantData.*;
import static ru.s32xlevel.data.UserData.USER_ID1;
import static ru.s32xlevel.data.VoteData.*;
import static ru.s32xlevel.util.DateTimeUtil.MAX_DATE;
import static ru.s32xlevel.util.DateTimeUtil.MIN_DATE;

public class VoteServiceTest extends AbstractServiceTest {
    private static final LocalTime NOW = LocalTime.now();

    @Autowired
    VoteService service;

    @Test
    public void vote() {
        Vote voted = service.vote(RES_ID2, USER_ID1, NOW);
        voted.setRestaurant(RES2);
        List<Vote> voices = service.getAllBetween(MIN_DATE, MAX_DATE);
        voices.sort(Comparator.comparing(Vote::getId));
        assertMatch(voices, VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, voted);
    }

    @Test
    public void secondVote() {
        service.vote(RES_ID2, USER_ID1, NOW);
        Vote voted = service.vote(RES_ID1, USER_ID1, LocalTime.of(10, 0));
        voted.setRestaurant(RES1);
        List<Vote> voices = service.getAllBetween(MIN_DATE, MAX_DATE);
        voices.sort(Comparator.comparing(Vote::getId));
        assertMatch(voices, VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, voted);
    }

    @Test(expected = NotFoundException.class)
    public void secondVoteError() {
        service.vote(RES_ID2, USER_ID1, NOW);
        service.vote(RES_ID1, USER_ID1, LocalTime.of(12, 0));
    }

    @Test
    public void getAll() {
        List<Vote> votes = service.getAll(DATE24);
        votes.sort(Comparator.comparing(Vote::getId));
        assertMatch(votes, VOTE1, VOTE2, VOTE3);
    }

    @Test
    public void getAllBetween() {
        List<Vote> votes = service.getAllBetween(DATE24, MAX_DATE);
        votes.sort(Comparator.comparing(Vote::getId));
        assertMatch(votes, VOTE1, VOTE2, VOTE3, VOTE4, VOTE5);
    }

    @Test
    public void get() {
        Vote vote = service.get(USER_ID1, DATE24);
        assertMatch(vote, VOTE1);
    }

    @Test
    public void getAllToRestaurant() {
        List<Vote> votes = service.getAllToRestaurant(DATE24, RES_ID1);
        votes.sort(Comparator.comparing(Vote::getId));
        assertMatch(votes, VOTE1, VOTE3);
    }

    @Test
    public void getAllToRestaurantHistory() {
        List<Vote> votes = service.getAllToRestaurantHistory(DATE24, MAX_DATE, RES_ID1);
        votes.sort(Comparator.comparing(Vote::getId));
        assertMatch(votes, VOTE1, VOTE3, VOTE4);
    }
}
