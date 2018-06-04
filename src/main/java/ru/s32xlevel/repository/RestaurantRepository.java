package ru.s32xlevel.repository;

import ru.s32xlevel.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    Restaurant get(int id);

    boolean delete(int id);

    List<Restaurant> getAll();
}
