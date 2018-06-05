package ru.s32xlevel.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.model.Vote;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.s32xlevel.data.RestaurantData.RES1;
import static ru.s32xlevel.data.RestaurantData.RES2;
import static ru.s32xlevel.data.UserData.ADMIN_ID;
import static ru.s32xlevel.data.UserData.USER_ID1;
import static ru.s32xlevel.data.UserData.USER_ID2;
import static ru.s32xlevel.web.json.JsonUtil.writeValue;

public class VoteData {
    public static final Vote VOTE1 = new Vote(100019, USER_ID1, LocalDate.of(2018, Month.MARCH, 24), new Restaurant(RES1));
    public static final Vote VOTE2 = new Vote(100020, USER_ID2, LocalDate.of(2018, Month.MARCH, 24), new Restaurant(RES2));
    public static final Vote VOTE3 = new Vote(100021, ADMIN_ID, LocalDate.of(2018, Month.MARCH, 24), new Restaurant(RES1));
    public static final Vote VOTE4 = new Vote(100022, USER_ID1, LocalDate.of(2018, Month.MARCH, 25), new Restaurant(RES1));
    public static final Vote VOTE5 = new Vote(100023, USER_ID2, LocalDate.of(2018, Month.MARCH, 25), new Restaurant(RES2));


    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Vote... expected) {
        return content().json(writeValue(Arrays.asList(expected)));
    }
}
