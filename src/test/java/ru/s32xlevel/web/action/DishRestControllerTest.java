package ru.s32xlevel.web.action;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.TestUtil;
import ru.s32xlevel.data.DishData;
import ru.s32xlevel.model.Dish;
import ru.s32xlevel.service.DishService;
import ru.s32xlevel.web.AbstractControllerTest;
import ru.s32xlevel.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.s32xlevel.TestUtil.userHttpBasic;
import static ru.s32xlevel.data.DishData.*;
import static ru.s32xlevel.data.RestaurantData.DATE24;
import static ru.s32xlevel.data.RestaurantData.RES_ID1;
import static ru.s32xlevel.data.UserData.ADMIN;
import static ru.s32xlevel.data.UserData.USER1;

public class DishRestControllerTest extends AbstractControllerTest {
    private static final String URL = DishRestController.REST_URL + "/";

    @Autowired
    private DishService service;

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(URL + RES_ID1 + "?date=2018-03-24")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH2, DISH1, DISH3));
    }

    @Test
    public void getIs() throws Exception {
        Dish expected = new Dish(DISH1);
        expected.setRestaurant(null);
        mockMvc.perform(get(URL + "is/" + DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(expected));
    }

    @Test
    public void copyMenu() throws Exception {
        mockMvc.perform(get(URL + "copy/" + RES_ID1 + "?date=2018-03-24")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertMatch(service.getAll(RES_ID1, LocalDate.now()), DishData.getNewEatToday());
    }

    @Test
    public void getBetween() throws Exception {
        mockMvc.perform(get(URL + "history/" + RES_ID1 + "?starDate=2018-03-24&endDate=2018-03-25")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH12, DISH2, DISH1, DISH3));
    }

    @Test
    public void getBetweenWidthNull() throws Exception {
        mockMvc.perform(get(URL + "history/" + RES_ID1 + "?starDate=")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH12, DISH3, DISH1, DISH3));
    }

    @Test
    public void create() throws Exception {
        Dish expected = new Dish(null, "Пельмени", 500);
        ResultActions action = mockMvc.perform(post(URL + RES_ID1)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        expected.setId(returned.getId());
        expected.setDate(returned.getDate());
        expected.setRestaurant(returned.getRestaurant());

        assertMatch(returned, expected);
        assertMatch(service.getAll(RES_ID1, LocalDate.now()), expected);
    }

    @Test
    public void update() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setPrice(321);
        updated.setName("vodka");

        mockMvc.perform(put(URL + RES_ID1 + "/" + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(service.get(DISH1_ID), updated);
    }



    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(RES_ID1, DATE24), DISH2, DISH3);
    }

    @Test
    public void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(URL + DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateForbidden() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("vodka");
        mockMvc.perform(put(URL + RES_ID1 + "/" + DISH1_ID)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateForbidden() throws Exception {
        Dish expected = new Dish(null, "Пельмени", 500);
        mockMvc.perform(post(URL + RES_ID1)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createValidError() throws Exception {
        Dish expected = new Dish(null, "", 500);
        MvcResult result = mockMvc.perform(post(URL + RES_ID1)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains(" must not be blank"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void createDuplicate() throws Exception {
        Dish expected = new Dish(null, "Мясо", 500);
        service.create(expected, RES_ID1);
        expected.setId(null);
        mockMvc.perform(post(URL + RES_ID1)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isConflict());
//                .andExpect(errorType(ErrorType.DATA_ERROR));
    }

    @Test
    public void updateValidError() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setDate(LocalDate.now());
        updated.setPrice(321);
        updated.setName("");

        MvcResult result = mockMvc.perform(put(URL + RES_ID1 + "/" + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains(" must not be blank"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void updateDuplicate() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setPrice(321);
        updated.setName("Пельмени");

        mockMvc.perform(put(URL + RES_ID1 + "/" + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isConflict());
//                .andExpect(errorType(ErrorType.DATA_ERROR));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(URL + ERROR_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setPrice(321);
        updated.setId(ERROR_ID);

        mockMvc.perform(put(URL + RES_ID1 + "/" + ERROR_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + "is/" + ERROR_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
