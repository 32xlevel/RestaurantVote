package ru.s32xlevel.web.action;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.s32xlevel.TestUtil;
import ru.s32xlevel.model.Vote;
import ru.s32xlevel.service.VoteService;
import ru.s32xlevel.util.DateTimeUtil;
import ru.s32xlevel.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.s32xlevel.TestUtil.userHttpBasic;
import static ru.s32xlevel.data.RestaurantData.*;
import static ru.s32xlevel.data.UserData.*;
import static ru.s32xlevel.data.VoteData.*;

public class VoteRestControllerTest extends AbstractControllerTest {
    private static final String URL = VoteRestController.REST_VOTE_URL + "/";

    @Autowired
    private VoteService service;

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(URL + "?date=2018-03-24")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE2, VOTE3));
    }

    @Test
    public void getBetween() throws Exception {
        mockMvc.perform(get(URL + "history?startDate=2018-03-24&endDate=2018-03-25")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5));
    }

    @Test
    public void getBetweenWithNull() throws Exception {
        mockMvc.perform(get(URL + "history?startDate=")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5));
    }

    @Test
    public void vote() throws Exception {
        ResultActions action = mockMvc.perform(post(URL + RES_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        Vote v = new Vote(0, USER1.getId(), LocalDate.now(), RES1);
        v.setId(returned.getId());

        assertMatch(service.getAllBetween(DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, v);
    }

    @Test
    public void getAllToRestaurant() throws Exception {
        mockMvc.perform(get(URL + RES_ID1 + "?date=2018-03-24")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE3));
    }

    @Test
    public void getAllToRestaurantHistory() throws Exception {
        mockMvc.perform(get(URL + "history/" + RES_ID1 + "?startDate=2018-03-24&endDate=2018-03-25")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE3, VOTE4));
    }

    @Test
    public void getAllToRestaurantNull() throws Exception {
        mockMvc.perform(get(URL + RES_ID1 + "?date=")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllToRestaurantHistoryNull() throws Exception {
        mockMvc.perform(get(URL + "history/" + RES_ID1 + "?startDate=")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE3, VOTE4));
    }

    @Test
    public void voiceNotAuthorized() throws Exception {
        mockMvc.perform(post(URL + RES_ID1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
