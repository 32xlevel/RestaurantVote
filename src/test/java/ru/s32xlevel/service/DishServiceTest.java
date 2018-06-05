package ru.s32xlevel.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.s32xlevel.model.Dish;
import ru.s32xlevel.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.s32xlevel.data.DishData.*;
import static ru.s32xlevel.data.RestaurantData.*;

public class DishServiceTest extends AbstractServiceTest {
    @Autowired
    private DishService service;

    @Test
    public void get() {
        Dish dish = service.get(DISH1_ID);
        assertMatch(dish, DISH1);
    }

    @Test
    public void delete() {
        service.delete(DISH1_ID);
        List<Dish> list = service.getAll(RES_ID1, DATE24);
        assertMatch(list, DISH2, DISH3);
    }

    @Test
    public void getAll() {
        List<Dish> list = service.getAll(RES_ID1, DATE24);
        assertMatch(list, DISH2, DISH3, DISH1);
    }

    @Test
    public void update() {
        Dish updated = new Dish(DISH1);
        updated.setName("Борщ");
        updated.setPrice(359);
        service.update(updated, RES_ID1);
        assertMatch(service.get(DISH1_ID), updated);
    }

    @Test
    public void create() {
        Dish newEat = new Dish(null, "Борщ", 350);
        Dish created = service.create(newEat, RES_ID1);
        newEat.setId(created.getId());
        newEat.setDate(created.getDate());
        assertMatch(service.getAll(RES_ID1, created.getDate()), newEat);
    }

    @Test
    public void getAllBetween() {
        List<Dish> list = service.getAllBetween(RES_ID1, DATE24, DATE25);
        assertMatch(list, DISH2, DISH3, DISH12, DISH1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(123);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("Борщ");
        updated.setId(123);
        service.update(updated, RES_ID1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundCreate() throws Exception {
        Dish newEat = new Dish(123, "Борщ", 350);
        service.create(newEat, RES_ID1);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreate() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("Суши");
        service.update(updated, RES_ID1);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, "  ", 300), RES_ID1), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, null, 300), RES_ID1), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "o", 500), RES_ID1), ConstraintViolationException.class);
    }
}
