package ru.s32xlevel.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.s32xlevel.model.Role;
import ru.s32xlevel.model.User;
import ru.s32xlevel.web.json.JsonUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.s32xlevel.web.json.JsonUtil.writeIgnoreProps;

public class UserData {
    public static final int USER_ID1 = 100000;
    public static final int USER_ID2 = 100001;
    public static final int ADMIN_ID = 100002;

    public static final User USER1 = new User(USER_ID1, "user1", "user1@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER2 = new User(USER_ID2, "user2", "user2@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "password"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "password"));
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
