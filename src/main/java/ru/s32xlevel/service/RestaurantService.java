package ru.s32xlevel.service;

import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {
    Restaurant get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<Restaurant> getAll();

    Restaurant update(Restaurant restaurant) throws NotFoundException;

    Restaurant create(Restaurant restaurant);
}
