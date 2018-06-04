package ru.s32xlevel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.s32xlevel.model.Dish;
import ru.s32xlevel.repository.DishRepository;
import ru.s32xlevel.service.DishService;
import ru.s32xlevel.util.DateTimeUtil;
import ru.s32xlevel.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.s32xlevel.util.ValidationUtil.checkNotFound;
import static ru.s32xlevel.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository repository;

    @Autowired
    public DishServiceImpl(DishRepository repository) {
        this.repository = repository;
    }

    @Override
    public Dish get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public List<Dish> getAll(int restaurantId, LocalDateTime date) {
        date = DateTimeUtil.nullToNow(date);
        return repository.getAll(restaurantId, date.toLocalDate());
    }

    @Override
    public Dish update(Dish dish, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    @Override
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "eat must not be null");
        return checkNotFound(repository.save(dish, restaurantId), "check of create dish");
    }

    @Override
    public List<Dish> getAllBetween(int id, LocalDateTime startDate, LocalDateTime endDate) {
        startDate = DateTimeUtil.nullToMin(startDate);
        endDate = DateTimeUtil.nullToMax(endDate);
        return repository.getAllBetween(startDate.toLocalDate(), endDate.toLocalDate(), id);
    }
}
