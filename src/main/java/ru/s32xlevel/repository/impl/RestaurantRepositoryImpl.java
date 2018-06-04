package ru.s32xlevel.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.repository.RestaurantRepository;

import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Autowired
    private CrudRestaurantRepository repository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }
}
