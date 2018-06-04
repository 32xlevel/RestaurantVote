package ru.s32xlevel.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.s32xlevel.model.Dish;
import ru.s32xlevel.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepositoryImpl implements DishRepository {

    @Autowired
    private CrudDishRepository dishRepository;

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Override
    public Dish save(Dish dish, int restaurantId) {
        if(!dish.isNew() && get(dish.getId()) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return dishRepository.delete(id) != 0;
    }

    @Override
    public Dish get(int id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public List<Dish> getAll(int restaurantId, LocalDate date) {
        return dishRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

    @Override
    public List<Dish> getAllBetween(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return dishRepository.getBetween(startDate, endDate, restaurantId);
    }
}
