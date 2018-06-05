package ru.s32xlevel.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.to.RestaurantWithVote;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.s32xlevel.web.json.JsonUtil.writeValue;

public class RestaurantData {
    public static final int RES_ID1 = 100003;
    public static final int RES_ID2 = 100004;
    public static final int RES_ID3 = 100005;
    public static final int RES_ID4 = 100006;

    public static final Restaurant RES1 = new Restaurant(RES_ID1, "Японский");
    public static final Restaurant RES2 = new Restaurant(RES_ID2, "Итальянский");
    public static final Restaurant RES3 = new Restaurant(RES_ID3, "Российский");
    public static final Restaurant RES4 = new Restaurant(RES_ID4, "Китайский");

    public static final LocalDate DATE24 = LocalDate.of(2018, Month.MARCH, 24);
    public static final LocalDate DATE25 = LocalDate.of(2018, Month.MARCH, 25);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu", "votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(List<RestaurantWithVote> expected) {
        return content().json(writeValue(expected));
    }

    public static ResultMatcher contentJson(RestaurantWithVote expected) {
        return content().json(writeValue(expected));
    }
}
