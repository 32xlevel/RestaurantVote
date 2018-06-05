package ru.s32xlevel.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.TestUtil;
import ru.s32xlevel.model.Role;
import ru.s32xlevel.model.User;
import ru.s32xlevel.service.UserService;
import ru.s32xlevel.web.AbstractControllerTest;
import ru.s32xlevel.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.s32xlevel.TestUtil.userHttpBasic;
import static ru.s32xlevel.data.UserData.*;

public class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestController.REST_URL + "/";

    @Autowired
    protected UserService userService;

    @Test
    public void testGet() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL)
                        .with(userHttpBasic(USER1)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(USER1))
        );
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER2);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_ID1, "new", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(jsonWithPassword(updated, "newPassword")))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(new User(userService.getByEmail("newemail@ya.ru")), updated);
    }

    @Test
    public void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateError() throws Exception {
        User updated = new User(USER1);
        updated.setEmail("");
        MvcResult result = mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains(" must not be blank"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testUpdateDuplicate() throws Exception {
        User updated = new User(USER1);
        updated.setName("qwerty");
        updated.setEmail(ADMIN.getEmail());

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isConflict());
//                .andExpect(errorType(ErrorType.DATA_ERROR));
    }
}