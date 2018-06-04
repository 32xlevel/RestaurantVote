package ru.s32xlevel.service;

import ru.s32xlevel.model.Dish;
import ru.s32xlevel.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DishService {
    Dish get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<Dish> getAll(int restaurantId, LocalDateTime date);

    Dish update(Dish dish, int restaurantId) throws NotFoundException;

    Dish create(Dish dish, int restaurantId);

    List<Dish> getAllBetween(int id, LocalDateTime startDate, LocalDateTime endDate);
}
