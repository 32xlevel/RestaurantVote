package ru.s32xlevel.web.json;

import org.junit.Assert;
import org.junit.Test;
import ru.s32xlevel.data.UserData;
import ru.s32xlevel.model.Dish;
import ru.s32xlevel.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static ru.s32xlevel.data.DishData.DISH1;
import static ru.s32xlevel.data.DishData.assertMatch;

public class JsonUtilTest {
    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(DISH1);
        System.out.println(json);
        Dish dish = JsonUtil.readValue(json, Dish.class);
        assertMatch(dish, DISH1);
    }

    @Test
    public void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserData.USER1);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserData.jsonWithPassword(UserData.USER1, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        Assert.assertEquals(user.getPassword(), "newPass");
    }
}
