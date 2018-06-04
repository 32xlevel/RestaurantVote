package ru.s32xlevel.util;

import ru.s32xlevel.model.Dish;
import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.to.RestaurantWithVote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    private static int voteCount(Restaurant restaurant, LocalDate startDate, LocalDate endDate) {
        return restaurant.getVotes() == null || restaurant.getVotes().size() == 0 ? 0 :
                (int) restaurant.getVotes().stream()
                        .filter(vote -> DateTimeUtil.isBetween(vote.getDate(), startDate, endDate))
                        .count();
    }

    private static int voteCount(Restaurant restaurant, LocalDate date) {
        return restaurant.getVotes() == null || restaurant.getVotes().size() == 0 ? 0 :
                (int) restaurant.getVotes().stream()
                        .filter(vote -> vote.getDate().isEqual(date))
                        .count();
    }

    private static List<Dish> menuDates(Restaurant restaurant, LocalDate startDate, LocalDate endDate) {
        if(restaurant.getMenu() != null && restaurant.getMenu().size() > 0) {
            restaurant.getMenu().forEach(dish -> dish.setRestaurant(null));
            return restaurant.getMenu().stream()
                    .filter(dish -> DateTimeUtil.isBetween(dish.getDate(), startDate, endDate))
                    .collect(toList());
        } else {
            return null;
        }
    }

    private static List<Dish> menuDate(Restaurant restaurant, LocalDate date) {
        if(restaurant.getMenu() != null && restaurant.getMenu().size() > 0) {
            restaurant.getMenu().forEach(dish -> dish.setRestaurant(null));
            return restaurant.getMenu().stream()
                    .filter(dish -> dish.getDate().isEqual(date))
                    .collect(toList());
        } else {
            return null;
        }
    }

    public static List<RestaurantWithVote> getAllWithoutMenu(List<Restaurant> list, LocalDate startDate, LocalDate endDate) {
        LocalDate inputStartDate = DateTimeUtil.nullToMin(startDate);
        LocalDate inputEndDate = DateTimeUtil.nullToMax(endDate);

        return list.stream().map(restaurant ->
                new RestaurantWithVote(restaurant, null, voteCount(restaurant, inputStartDate, inputEndDate)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAllWithoutMenu(List<Restaurant> list, LocalDate date) {
        LocalDate inputDate = DateTimeUtil.nullToNow(date);

        return list.stream().map(restaurant ->
                new RestaurantWithVote(restaurant, null, voteCount(restaurant, inputDate)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAll(List<Restaurant> list,
                                                  LocalDate inputStartDate,
                                                  LocalDate inputEndDate,
                                                  LocalDate inputStartDateMenu,
                                                  LocalDate inputEndDateMenu) {

        LocalDate startDate = DateTimeUtil.nullToMin(inputStartDate);
        LocalDate endDate = DateTimeUtil.nullToMax(inputEndDate);
        LocalDate startDateMenu = DateTimeUtil.nullToMin(inputStartDateMenu);
        LocalDate endDateMenu = DateTimeUtil.nullToMax(inputEndDateMenu);

        return list.stream().map(i -> new RestaurantWithVote(i, menuDates(i, startDateMenu, endDateMenu), voteCount(i, startDate, endDate)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAll(List<Restaurant> list,
                                                  LocalDate inputStartDate,
                                                  LocalDate inputEndDate,
                                                  LocalDate inputDateMenu) {
        LocalDate date = DateTimeUtil.nullToNow(inputDateMenu);
        LocalDate startDate = DateTimeUtil.nullToMin(inputStartDate);
        LocalDate endDate = DateTimeUtil.nullToMax(inputEndDate);
        return list.stream().map(i -> new RestaurantWithVote(i, menuDate(i, date), voteCount(i, startDate, endDate)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAll(List<Restaurant> list,
                                                  LocalDate inputDate) {
        LocalDate date = DateTimeUtil.nullToNow(inputDate);
        return list.stream().map(i -> new RestaurantWithVote(i, menuDate(i, date), voteCount(i, date)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAllWithMenu(List<Restaurant> list,
                                                          LocalDate inputDate) {
        LocalDate date = DateTimeUtil.nullToNow(inputDate);

        return list.stream().map(i -> {
            Integer count = i.getVotes() == null ? 0 : i.getVotes().size();
            return new RestaurantWithVote(i, menuDate(i, date), count);
        }).collect(toList());
    }

    public static RestaurantWithVote get(Restaurant restaurant) {
        return new RestaurantWithVote(restaurant);
    }

    public static List<RestaurantWithVote> getAll(List<Restaurant> list) {
        return list.stream().map(RestaurantWithVote::new).collect(toList());
    }
}
