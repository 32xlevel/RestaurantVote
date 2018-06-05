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
import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.service.RestaurantService;
import ru.s32xlevel.util.RestaurantUtil;
import ru.s32xlevel.web.AbstractControllerTest;
import ru.s32xlevel.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.s32xlevel.TestUtil.userHttpBasic;
import static ru.s32xlevel.data.RestaurantData.*;
import static ru.s32xlevel.data.UserData.ADMIN;
import static ru.s32xlevel.data.UserData.USER1;

public class RestaurantRestControllerTest extends AbstractControllerTest {
    private static final String URL = "/restaurant/";

    @Autowired
    private RestaurantService service;

    @Test
    public void getAllToData() throws Exception {
        mockMvc.perform(get(URL + "date?date=2018-03-24")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getAllWithoutMenu(Arrays.asList(RES1, RES2, RES4, RES3),
                        DATE24)));
    }

    @Test
    public void getIs() throws Exception {
        mockMvc.perform(get(URL + RES_ID1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.get(RES1)));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getAll(Arrays.asList(RES1, RES2, RES4, RES3))));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void getAllWidthMenu() throws Exception {
        mockMvc.perform(get(URL + "menu?date=2018-03-24")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getAllWithMenu(Arrays.asList(RES1, RES2, RES4, RES3),DATE24)));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void getAllWidthMenuToDate() throws Exception {
        mockMvc.perform(get(URL + "date/menu?date=2018-03-24")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getAll(Arrays.asList(RES1, RES2, RES4, RES3), DATE24)));
    }


    @Test
    public void getAllBetweenWidthOutMenu() throws Exception {
        mockMvc.perform(get(URL + "history?startDate=2018-03-24&endDate=2018-03-25")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getAllWithoutMenu(Arrays.asList(RES1, RES2, RES4, RES3), DATE24, DATE25)));
    }

    @Test
    public void getAllBetweenOfVoices() throws Exception {
        mockMvc.perform(get(URL + "history/menu?startDate=2018-03-24&endDate=2018-03-25" +
                "&dateMenu=2018-03-24")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getAll(Arrays.asList(RES1, RES2, RES4, RES3), DATE24, DATE25, DATE24)));
    }

    @Test
    public void getAllBetweenOfVoicesAndMenu() throws Exception {
        mockMvc.perform(get(URL + "history/menu/history?startDateVoice=2018-03-24&endDateVoice=2018-03-25" +
                "&startDateMenu=2018-03-24&endDateMenu=2018-03-25")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getAll(Arrays.asList(RES1, RES2, RES4, RES3), DATE24, DATE25, DATE24, DATE25)));
    }

    @Test
    public void add() throws Exception {
        Restaurant expected = new Restaurant(null, "Диззи");
        ResultActions action = mockMvc.perform(post(URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(), expected, RES1, RES2, RES4, RES3);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + RES_ID1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), RES2, RES4, RES3);
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = new Restaurant(RES1);
        updated.setName("KFC");
        mockMvc.perform(put(URL + RES_ID1)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(service.get(RES_ID1), updated);
    }

    @Test
    public void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(URL + RES_ID1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateForbidden() throws Exception {
        Restaurant updated = new Restaurant(RES1);
        updated.setName("KFC");
        mockMvc.perform(put(URL + RES_ID1)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateForbidden() throws Exception {
        Restaurant expected = new Restaurant(null, "Диззи");
        mockMvc.perform(post(URL)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addValidError() throws Exception {
        Restaurant expected = new Restaurant(null, "");
        MvcResult result = mockMvc.perform(post(URL)
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
    public void addDuplicate() throws Exception {
        Restaurant expected = new Restaurant(null, "Палкин");
        mockMvc.perform(post(URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isConflict());
//                .andExpect(errorType(ErrorType.DATA_ERROR));
    }

    @Test
    public void updateValidError() throws Exception {
        Restaurant updated = new Restaurant(RES1);
        updated.setName("");
        MvcResult result = mockMvc.perform(put(URL + RES_ID1)
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
        Restaurant updated = new Restaurant(RES1);
        updated.setName("Россия");
        mockMvc.perform(put(URL + RES_ID1)
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
        Restaurant updated = new Restaurant(RES1);
        updated.setName("KFC");
        updated.setId(ERROR_ID);
        mockMvc.perform(put(URL + ERROR_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + ERROR_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
