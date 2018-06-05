package ru.s32xlevel.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.s32xlevel.model.Dish;
import ru.s32xlevel.model.Restaurant;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.s32xlevel.data.RestaurantData.*;
import static ru.s32xlevel.web.json.JsonUtil.writeValue;

public class DishData {
    public static final int DISH1_ID = 100007;
    public static final int DISH2_ID = 100008;
    public static final int DISH3_ID = 100009;

    public static final Dish DISH1 = new Dish(100007, LocalDate.of(2018, Month.MARCH, 24), "Суши", 500, new Restaurant(RES1));
    public static final Dish DISH2 = new Dish(100008, LocalDate.of(2018, Month.MARCH, 24), "Напиток", 700, new Restaurant(RES1));
    public static final Dish DISH3 = new Dish(100009, LocalDate.of(2018, Month.MARCH, 24), "Напиток2", 100, new Restaurant(RES1));
    public static final Dish DISH4 = new Dish(100010, LocalDate.of(2018, Month.MARCH, 24), "Макароны", 1200, new Restaurant(RES2));
    public static final Dish DISH5 = new Dish(100011, LocalDate.of(2018, Month.MARCH, 24), "Паста", 1500, new Restaurant(RES2));
    public static final Dish DISH6 = new Dish(100012, LocalDate.of(2018, Month.MARCH, 24), "Пельмени", 800, new Restaurant(RES3));
    public static final Dish DISH7 = new Dish(100013, LocalDate.of(2018, Month.MARCH, 24), "Пюре", 400, new Restaurant(RES3));
    public static final Dish DISH8 = new Dish(100014, LocalDate.of(2018, Month.MARCH, 24), "Салат", 700, new Restaurant(RES3));
    public static final Dish DISH9 = new Dish(100015, LocalDate.of(2018, Month.MARCH, 24), "Картошка", 400, new Restaurant(RES3));
    public static final Dish DISH10 = new Dish(100016, LocalDate.of(2018, Month.MARCH, 24), "Роллы", 600, new Restaurant(RES4));
    public static final Dish DISH11 = new Dish(100017, LocalDate.of(2018, Month.MARCH, 24), "Суп", 1100, new Restaurant(RES4));
    public static final Dish DISH12 = new Dish(100018, LocalDate.of(2018, Month.MARCH, 25), "Жаркое", 350, new Restaurant(RES1));

    private static Comparator<Restaurant> restaurantComparator = (o1, o2) ->
            o1 == null || o2 == null || o1.getId().equals(o2.getId()) ? 0 : 1;

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
        assertThat(actual).usingComparatorForFields(restaurantComparator, "restaurant").isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
        assertThat(actual).usingComparatorForElementFieldsWithNames(restaurantComparator, "restaurant")
                .usingFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Dish... expected) {
        return content().json(writeValue(Arrays.asList(expected)));
    }

    public static ResultMatcher contentJson(Dish expected) {
        return content().json(writeValue(expected));
    }

    public static List<Dish> getNewEatToday() {
        List<Dish> list = Arrays.asList(new Dish(DISH2), new Dish(DISH1), new Dish(DISH3));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setDate(LocalDate.now());
            list.get(i).setId(100024 + i);
        }
        return list;
    }
}
