package ru.s32xlevel;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.s32xlevel.model.User;
import ru.s32xlevel.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static ru.s32xlevel.data.DishData.*;
import static ru.s32xlevel.data.RestaurantData.*;
import static ru.s32xlevel.data.VoteData.*;

public class TestUtil {
    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static void setProperty() {
        RES1.setVotes(Arrays.asList(VOTE1, VOTE3, VOTE4));
        RES2.setVotes(Arrays.asList(VOTE2, VOTE5));
        RES1.setMenu(Arrays.asList(DISH1, DISH2, DISH3, DISH12));
        RES2.setMenu(Arrays.asList(DISH4, DISH5));
        RES3.setMenu(Arrays.asList(DISH6, DISH7, DISH8, DISH9));
        RES4.setMenu(Arrays.asList(DISH10, DISH11));
    }
}
