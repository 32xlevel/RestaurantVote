package ru.s32xlevel.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.TestUtil;
import ru.s32xlevel.model.Role;
import ru.s32xlevel.model.User;
import ru.s32xlevel.service.UserService;
import ru.s32xlevel.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.s32xlevel.TestUtil.userHttpBasic;
import static ru.s32xlevel.data.UserData.*;
import static ru.s32xlevel.web.user.Registration.REGISTRATION_URL;

public class RegistrationTest extends AbstractControllerTest {
    private static final String REST_URL = REGISTRATION_URL + "/";

    @Autowired
    protected UserService userService;

    @Test
    public void createNewUser() throws Exception {
        User expected = new User(null, "new", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(expected, "newPass")))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER1, USER2);
    }

    @Test
    public void testCreateError() throws Exception {
        User expected = new User(null, "New", "", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        MvcResult result = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(expected, "newPass")))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains(" must not be blank"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateDuplicate() throws Exception {
        User expected = new User(null, "New", "user1@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(expected, "newPass")))
                .andDo(print())
                .andExpect(status().isConflict());
//                .andExpect(errorType(ErrorType.DATA_ERROR));
    }
}