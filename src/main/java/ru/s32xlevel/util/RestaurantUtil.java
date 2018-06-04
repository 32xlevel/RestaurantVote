package ru.s32xlevel.util;

import ru.s32xlevel.model.Dish;
import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.repository.RestaurantRepository;
import ru.s32xlevel.repository.impl.RestaurantRepositoryImpl;
import ru.s32xlevel.to.RestaurantWithVote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    private static int voteCount(Restaurant restaurant, LocalDate startDate, LocalDate endDate) {
        return restaurant.getVotes() == null || restaurant.getVotes().size() == 0 ? 0 :
                (int) restaurant.getVotes().stream()
                        .filter(vote -> DateTimeUtil.isBetween(vote.getDateTime().toLocalDate(), startDate, endDate))
                        .count();
    }

    private static int voteCount(Restaurant restaurant, LocalDate date) {
        return restaurant.getVotes() == null || restaurant.getVotes().size() == 0 ? 0 :
                (int) restaurant.getVotes().stream()
                        .filter(vote -> vote.getDateTime().toLocalDate().isEqual(date))
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

    public static List<RestaurantWithVote> getAllWithoutMenu(List<Restaurant> list, LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime inputStartDate = DateTimeUtil.nullToMin(startDate);
        LocalDateTime inputEndDate = DateTimeUtil.nullToMax(endDate);

        return list.stream().map(restaurant ->
                new RestaurantWithVote(restaurant, null, voteCount(restaurant, inputStartDate.toLocalDate(), inputEndDate.toLocalDate())))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAllWithoutMenu(List<Restaurant> list, LocalDateTime date) {
        LocalDate inputDate = DateTimeUtil.nullToNow(date).toLocalDate();

        return list.stream().map(restaurant ->
                new RestaurantWithVote(restaurant, null, voteCount(restaurant, inputDate)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAll(List<Restaurant> list,
                                                  LocalDateTime inputStartDate,
                                                  LocalDateTime inputEndDate,
                                                  LocalDateTime inputStartDateMenu,
                                                  LocalDateTime inputEndDateMenu) {

        LocalDate startDate = DateTimeUtil.nullToMin(inputStartDate).toLocalDate();
        LocalDate endDate = DateTimeUtil.nullToMax(inputEndDate).toLocalDate();
        LocalDate startDateMenu = DateTimeUtil.nullToMin(inputStartDateMenu).toLocalDate();
        LocalDate endDateMenu = DateTimeUtil.nullToMax(inputEndDateMenu).toLocalDate();

        return list.stream().map(i -> new RestaurantWithVote(i, menuDates(i, startDateMenu, endDateMenu), voteCount(i, startDate, endDate)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAll(List<Restaurant> list,
                                                  LocalDateTime inputStartDate,
                                                  LocalDateTime inputEndDate,
                                                  LocalDateTime inputDateMenu) {
        LocalDate date = DateTimeUtil.nullToNow(inputDateMenu).toLocalDate();
        LocalDate startDate = DateTimeUtil.nullToMin(inputStartDate).toLocalDate();
        LocalDate endDate = DateTimeUtil.nullToMax(inputEndDate).toLocalDate();
        return list.stream().map(i -> new RestaurantWithVote(i, menuDate(i, date), voteCount(i, startDate, endDate)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAll(List<Restaurant> list,
                                                  LocalDateTime inputDate) {
        LocalDate date = DateTimeUtil.nullToNow(inputDate).toLocalDate();
        return list.stream().map(i -> new RestaurantWithVote(i, menuDate(i, date), voteCount(i, date)))
                .collect(toList());
    }

    public static List<RestaurantWithVote> getAllWithMenu(List<Restaurant> list,
                                                           LocalDateTime inputDate) {
        LocalDate date = DateTimeUtil.nullToNow(inputDate).toLocalDate();

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
