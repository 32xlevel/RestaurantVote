package ru.s32xlevel.repository;

import ru.s32xlevel.model.Dish;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DishRepository {
    // null if updated meal do not belong to userId
    Dish save(Dish dish, int restaurantId);

    // false if meal do not belong to userId
    boolean delete(int id);

    // null if meal do not belong to userId
    Dish get(int id);

    List<Dish> getAll(int restaurantId, LocalDate date);

    List<Dish> getAllBetween(LocalDate startDate, LocalDate endDate, int restaurantId);
}
